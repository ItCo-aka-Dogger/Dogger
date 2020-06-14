package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.NewOwnerDto;
import ru.itis.dogger.services.UsersService;

@RestController
public class PasswordRecoverController {

    private UsersService usersService;

    @Autowired
    public PasswordRecoverController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/recover/{userId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> recoverPassword(@PathVariable Long userId) {
        if (usersService.recover(userId)) {
            return ResponseEntity.ok("Password recovered");
        } else {
            return ResponseEntity.ok("Password recover process is not finished");
        }
    }

    @PostMapping("/recover")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> forgotPassword(@RequestBody NewOwnerDto dto) {
        if (usersService.findByEmail(dto.getEmail().isPresent()) {
            usersService.sendRecoverMail(dto.getEmail());
            return ResponseEntity.ok("Recover mail is sent. Check your mailbox");
        } else {
            return ResponseEntity.ok("No user with such email or login. Please verify your data.");
        }
    }
}
