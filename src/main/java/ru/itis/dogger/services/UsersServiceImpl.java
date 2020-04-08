package ru.itis.dogger.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.itis.dogger.dto.EditDto;
import ru.itis.dogger.dto.OwnerDto;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.UsersRepository;

import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Qualifier("getJavaMailSender")
    @Autowired
    public JavaMailSender emailSender;

    @Override
    public boolean signUp(OwnerDto dto) {
        Optional<Owner> dbUser = usersRepository.findByLogin(dto.getLogin());
        if (dbUser.isPresent()) {
            return false;
        }
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Owner newUser = new Owner(dto.getLogin(), hashPassword, dto.getFullName(), dto.getEmail());
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setActive(true);
        usersRepository.save(newUser);

        if (!StringUtils.isEmpty(newUser.getEmail())) {
            String message = "Hello, \n" +
                            "Welcome to Dogger. Please, visit next link: http://localhost:8080/activate/" +
                    newUser.getActivationCode();
//            emailService.sendMail(newUser.getEmail(), "Activation code", message);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(newUser.getEmail());
            mailMessage.setSubject("Activation code");
            mailMessage.setText(message);
            emailSender.send(mailMessage);
        }
        return true;
    }

    @Override
    public TokenDto login(OwnerDto dto) {
        Optional<Owner> userCandidate = usersRepository.findByLogin(dto.getLogin());
        if (userCandidate.isPresent()) {
            Owner user = userCandidate.get();
            return new TokenDto(createToken(user));
        }
        throw new NoSuchElementException("Can not find such user");
    }

    @Override
    public Optional<Owner> findByLogin(String login) {
        return usersRepository.findByLogin(login);
    }

    @Override
    public Map<String, Object> userToMap(Owner owner) {
        Map<String, Object> ownerProperties = new HashMap<>();
        ownerProperties.put("login", owner.getLogin());
        ownerProperties.put("fullName", owner.getFullName());
        ownerProperties.put("dateOfBirth", owner.getDateOfBirth());
        ownerProperties.put("dogs", owner.getDogs());
        ownerProperties.put("meetings", owner.getMeetings());
        return ownerProperties;
    }

    @Override
    public void editInfo(EditDto dto, String login) {
        Owner dbOwner = usersRepository.findByLogin(login).get();
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        dbOwner.setLogin(dto.getLogin());
        dbOwner.setPassword(hashPassword);
        dbOwner.setFullName(dto.getFullName());
        dbOwner.setDateOfBirth(dto.getDateOfBirth());
        usersRepository.save(dbOwner);
    }

    @Override
    public boolean activateUser(String code) {
        Optional<Owner> user = usersRepository.findByActivationCode(code);
        if (!user.isPresent()) {
            return false;
        }
        user.get().setActivationCode(null);
        usersRepository.save(user.get());
        return true;
    }

    private String createToken(Owner user) {
        return Jwts.builder()
                .claim("login", user.getLogin())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
