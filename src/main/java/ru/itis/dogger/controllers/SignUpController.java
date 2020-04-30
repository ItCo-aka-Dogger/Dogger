package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.NewOwnerDto;
import ru.itis.dogger.dto.ResponseDto;
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
            return ResponseEntity.ok(new ResponseDto("User already in the system"));
        } else {
            return ResponseEntity.ok(new ResponseDto("Successfully added user"));
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activate(@PathVariable String code) {
        boolean isActivated = usersService.activateUser(code);

        if (isActivated) {
            return ResponseEntity.ok(new ResponseDto("User successfully activated"));
        } else {
            return ResponseEntity.ok(new ResponseDto("User not found"));
        }
    }
}
