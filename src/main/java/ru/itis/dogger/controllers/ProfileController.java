package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.models.Owner;
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
}
