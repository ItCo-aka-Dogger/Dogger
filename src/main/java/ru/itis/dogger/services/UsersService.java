package ru.itis.dogger.services;

import org.springframework.security.core.Authentication;
import ru.itis.dogger.dto.EditDto;
import ru.itis.dogger.dto.NewOwnerDto;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.models.Owner;

import java.util.Map;
import java.util.Optional;

public interface UsersService {
    boolean signUp(NewOwnerDto dto);

    TokenDto login(NewOwnerDto dto);

    Optional<Owner> findByLogin(String login);

    Map<String, Object> userToMap(Owner owner);

    void editInfo(EditDto dto, String login);

    boolean activateUser(String code);

    Optional<Owner> findByEmail(String email);

    void sendRecoverMail(String email);

    boolean recover(Long userId);

    String delete(String login);

    Optional<Owner> getCurrentUser(Authentication authentication);

    Optional<Owner> getUserById(Long id);
}
