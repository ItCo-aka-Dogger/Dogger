package ru.itis.dogger.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.itis.dogger.dto.EditDto;
import ru.itis.dogger.dto.NewOwnerDto;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.UsersRepository;
import ru.itis.dogger.security.details.UserDetailsImpl;

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
    public boolean signUp(NewOwnerDto dto) {
        Optional<Owner> dbUser = usersRepository.findByEmail(dto.getEmail());
        if (dbUser.isPresent()) {
            return false;
        }
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Owner newUser = Owner.builder()
                .password(hashPassword)
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .city(dto.getCity())
                .build();
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
    public TokenDto login(NewOwnerDto dto) {
        Optional<Owner> userCandidate = usersRepository.findByEmail(dto.getEmail());
        if (userCandidate.isPresent()) {
            Owner user = userCandidate.get();
            return new TokenDto(createToken(user));
        }
        throw new NoSuchElementException("Can not find such user");
    }

    @Override
    public Optional<Owner> findByLogin(String login) {
        return usersRepository.findByEmail(login);
    }

    @Override
    public Map<String, Object> userToMap(Owner owner) {
        Map<String, Object> ownerProperties = new HashMap<>();
        ownerProperties.put("email", owner.getEmail());
        ownerProperties.put("id", owner.getId());
        ownerProperties.put("fullName", owner.getFullName());
        ownerProperties.put("dateOfBirth", owner.getDateOfBirth());
        ownerProperties.put("dogs", owner.getDogs());
        ownerProperties.put("meetings", owner.getMeetings());
        return ownerProperties;
    }

    @Override
    public void editInfo(EditDto dto, String email) {
        Owner dbOwner = usersRepository.findByEmail(email).get();
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        dbOwner.setEmail(dto.getEmail());
        dbOwner.setPassword(hashPassword);
        dbOwner.setFullName(dto.getFullName());
        dbOwner.setDateOfBirth(dto.getDateOfBirth());
        dbOwner.setCity(dto.getCity());
        dbOwner.setPhoneNumber(dto.getPhoneNumber());
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
    public String delete(Long id) {
        Optional<Owner> owner = usersRepository.findById(id);
        if (owner.isPresent()) {
            usersRepository.delete(owner.get());
            return "User successfully deleted";
        } else {
            return "No such user in db";
        }
    }

    @Override
    public Optional<Owner> getCurrentUser(Authentication authentication) {
        if (authentication != null) {
            Long currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getId();
            return usersRepository.findById(currentUserId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Owner> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    private String createToken(Owner user) {
        return Jwts.builder()
                .claim("login", user.getEmail())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
