package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.EditDto;
import ru.itis.dogger.dto.DogDto;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.DogsService;
import ru.itis.dogger.services.UsersService;

import java.util.Optional;

@RestController
public class ProfileController {

    private UsersService usersService;

    @Autowired
    public ProfileController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProfilePage(@RequestHeader(name = "Authorization") String token, Authentication authentication) {
        Optional<Owner> userCandidate = usersService.findByLogin(authentication.getName());
        if (userCandidate.isPresent()) {
            return ResponseEntity.ok(usersService.userToMap(userCandidate.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editProfile(@RequestBody EditDto dto, Authentication authentication,
                                         @RequestHeader(name = "Authorization") String token) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        usersService.editInfo(dto, currentUser.getLogin());
        return ResponseEntity.ok(usersService.findByLogin(dto.getLogin()));
    }
}
