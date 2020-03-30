package ru.itis.dogger.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.OwnerDto;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.UsersRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    @Value("{jwt.secret}")
    private String KEY;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(OwnerDto dto) {
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Owner newUser = new Owner(dto.getLogin(), hashPassword, dto.getFullName());
        usersRepository.save(newUser);
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

    private String createToken(Owner user) {
        return Jwts.builder()
                .claim("login", user.getLogin())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

}
