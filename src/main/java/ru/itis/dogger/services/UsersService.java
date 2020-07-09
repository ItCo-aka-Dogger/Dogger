package ru.itis.dogger.services;

import org.springframework.security.core.Authentication;
import ru.itis.dogger.dto.owner.EditUserInfoDto;
import ru.itis.dogger.dto.owner.NewOwnerDto;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.models.place.Place;

import java.util.Optional;

public interface UsersService {
    boolean signUp(NewOwnerDto dto);

    TokenDto login(NewOwnerDto dto);

    Optional<Owner> findByLogin(String login);

    void editInfo(EditUserInfoDto dto, String login);

    boolean activateUser(String code);

    Optional<Owner> findByEmail(String email);

    void sendRecoverMail(String email);

    boolean recover(Long userId);

    void delete(Owner user);

    Optional<Owner> getCurrentUser(Authentication authentication);

    Optional<Owner> getUserById(Long id);

    boolean checkForUniqueness(String email);

    TokenDto changeEmail(String email, Owner currentUser);

    void changePassword(String password, Owner currentUser);

    void addPlaceToFavorites(Place place, Owner currentUser);

    void removePlaceFromFavorites(Place place, Owner currentUser);
}
