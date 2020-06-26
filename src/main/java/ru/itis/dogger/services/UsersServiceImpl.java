package ru.itis.dogger.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.itis.dogger.dto.CredentialsDto;
import ru.itis.dogger.dto.EditUserInfoDto;
import ru.itis.dogger.dto.NewOwnerDto;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.enums.Contact;
import ru.itis.dogger.enums.TokenStatus;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.UsersRepository;
import ru.itis.dogger.security.details.UserDetailsImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
        String email = dto.getEmail();
        Optional<Owner> dbUser = usersRepository.findByEmail(email.toLowerCase());
        if (dbUser.isPresent()) {
            return false;
        }
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Owner newUser = Owner.builder()
                .email(email.toLowerCase())
                .password(hashPassword)
                .name(dto.getName())
                .fullName(dto.getFullName())
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
        String email = dto.getEmail();
        Optional<Owner> userCandidate = usersRepository.findByEmail(email.toLowerCase());
        TokenDto tokenDto = new TokenDto();
        if (userCandidate.isPresent()) {
            Owner user = userCandidate.get();
            if (BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
                tokenDto.setValue(createToken(user));
                tokenDto.setStatus(TokenStatus.VALID);
            } else {
                tokenDto.setValue("Incorrect password");
                tokenDto.setStatus(TokenStatus.INVALID);
            }
        } else {
            tokenDto.setValue("Can't find user with this email");
            tokenDto.setStatus(TokenStatus.INVALID);
        }
        return tokenDto;
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
    public TokenDto editInfo(EditUserInfoDto dto, String email) {
        Owner dbOwner = usersRepository.findByEmail(email).get();
        dbOwner.setFullName(dto.getFullName());
        dbOwner.setDateOfBirth(dto.getDateOfBirth());
        dbOwner.setCity(dto.getCity());
        dbOwner.setPhoneNumber(dto.getPhoneNumber());
        dbOwner.setDistrict(dto.getDistrict());
        Map<Contact, String> contacts = new HashMap<>();
        for (Map.Entry<String, String> e : dto.getContacts().entrySet()) {
            contacts.put(Contact.valueOf(e.getKey().toUpperCase()), e.getValue());
        }
        dbOwner.setContacts(contacts);
        usersRepository.save(dbOwner);
        return refreshToken(dbOwner);
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

    @Override
    public boolean checkForUniqueness(String email) {
        return !usersRepository.findByEmail(email).isPresent();
    }

    @Override
    public TokenDto changeCreds(CredentialsDto dto, Owner currentUser) {
        currentUser.setEmail(dto.getEmail());
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        currentUser.setPassword(hashedPassword);
        usersRepository.save(currentUser);
        return refreshToken(currentUser);
    }

    private String createToken(Owner user) {
        return Jwts.builder()
                .claim("login", user.getEmail())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private TokenDto refreshToken(Owner dbOwner) {
        TokenDto newToken = new TokenDto();
        newToken.setStatus(TokenStatus.VALID);
        newToken.setValue(createToken(dbOwner));
        return newToken;
    }
}
