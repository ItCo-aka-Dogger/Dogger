package ru.itis.dogger.services;

import org.springframework.http.ResponseEntity;
import ru.itis.dogger.dto.EditDto;
import ru.itis.dogger.dto.OwnerDto;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.models.Owner;

import java.util.Map;
import java.util.Optional;

public interface UsersService {
    void signUp(OwnerDto dto);

    TokenDto login(OwnerDto dto);

    Optional<Owner> findByLogin(String login);

    Map<String, Object> userToMap(Owner owner);

    void editInfo(EditDto dto, String login);
}
