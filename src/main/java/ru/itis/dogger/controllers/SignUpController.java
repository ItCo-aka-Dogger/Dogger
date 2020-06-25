package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.NewOwnerDto;
import ru.itis.dogger.services.UsersService;

import javax.validation.Valid;

@RestController
public class SignUpController {

    private UsersService usersService;

    @Autowired
    public SignUpController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public ResponseEntity<?> signUpNewUser(@Valid @RequestBody NewOwnerDto dto) {
        if (!usersService.signUp(dto)) {
            return new ResponseEntity<>("This email is already in use", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Successfully added user", HttpStatus.OK);
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activate(@PathVariable String code) {
        boolean isActivated = usersService.activateUser(code);
        if (isActivated) {
            return new ResponseEntity<>("User successfully activated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
        }
    }
}
