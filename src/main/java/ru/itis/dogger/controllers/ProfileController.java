package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.dto.owner.EditUserInfoDto;
import ru.itis.dogger.dto.owner.OwnerDto;
import ru.itis.dogger.enums.TokenStatus;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.UsersService;

import java.util.Map;
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
    public ResponseEntity<?> getProfilePage(Authentication authentication) {
        Optional<Owner> userCandidate = usersService.findByLogin(authentication.getName());
        if (userCandidate.isPresent()) {
            return ResponseEntity.ok(OwnerDto.from(userCandidate.get()));
        } else {
            return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/profile/{userId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAnotherUserProfilePage(@PathVariable String userId) {
        Optional<Owner> userCandidate = usersService.getUserById(userId);
        if (userCandidate.isPresent()) {
            return ResponseEntity.ok(OwnerDto.from(userCandidate.get()));
        } else {
            return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/editUserInfo")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editProfile(@RequestBody EditUserInfoDto dto, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        try {
            usersService.editInfo(dto, currentUser.getEmail());
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("No user with such email or login. Please verify your data.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usersService.findByEmail(currentUser.getEmail()));
    }

    @PostMapping("/editEmail")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editEmail(@RequestBody Map<String, String> dto, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        TokenDto tokenDto = usersService.changeEmail(dto.get("email"), currentUser);
        if (tokenDto.getStatus().equals(TokenStatus.INVALID)) {
            return new ResponseEntity<>("Email is already taken", HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/editPassword")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editPassword(@RequestBody Map<String, String> dto, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        usersService.changePassword(dto.get("password"), currentUser);
        return ResponseEntity.ok(usersService.findByEmail(currentUser.getEmail()));
    }

    @PostMapping("/delete")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") String userId) {
        Optional<Owner> user = usersService.getUserById(userId);
        if (user.isPresent()) {
            usersService.delete(user.get());
            return new ResponseEntity<>("User was successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no user with such id", HttpStatus.NOT_FOUND);
        }
    }
}
