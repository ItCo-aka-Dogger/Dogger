package ru.itis.dogger.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private EmailService emailService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Override
    public boolean signUp(OwnerDto dto) {
        Optional<Owner> dbUser = usersRepository.findByLogin(dto.getLogin());
        if (dbUser.isPresent()) {
            return false;
        }
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Owner newUser = new Owner(dto.getLogin(), hashPassword, dto.getFullName(), dto.getEmail());
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setActive(false);
        usersRepository.save(newUser);

        if (!StringUtils.isEmpty(newUser.getEmail())) {
            String message = "Hello, \n" +
                    "Welcome to Dogger. Please, visit next link: https://gentle-plains-10374.herokuapp.com/activate/" +
                    newUser.getActivationCode();
            emailService.sendMail(newUser.getEmail(), "Activation code", message);
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
        user.get().setActive(true);
        usersRepository.save(user.get());
        return true;
    }

    @Override
    public Optional<Owner> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public void sendRecoverMail(String email) {
        Owner user = usersRepository.findByEmail(email).get();
        user.setActive(false);

        String message = "Hello, \n" +
                "to recover your password, please, visit next link: http://localhost:8080/recover/" +
                user.getId();
        emailService.sendMail(user.getEmail(), "Password recover", message);
        usersRepository.save(user);
    }

    @Override
    public boolean recover(Long userId) {
        Optional<Owner> userCandidate = usersRepository.findById(userId);
        if (userCandidate.isPresent()) {
            userCandidate.get().setActive(true);
            usersRepository.save(userCandidate.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String delete(String login) {
        Optional<Owner> owner = usersRepository.findByLogin(login);
        if (owner.isPresent()) {
            usersRepository.delete(owner.get());
            return "User successfully deleted";
        } else {
            return "No such user in db";
        }
    }

    private String createToken(Owner user) {
        return Jwts.builder()
                .claim("login", user.getLogin())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
