package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.ResponseDto;
import ru.itis.dogger.dto.owner.NewOwnerDto;
import ru.itis.dogger.services.UsersService;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class SignUpController {

    private UsersService usersService;

    @Autowired
    public SignUpController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/checkUserEmail")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> dto) {
        if (!usersService.checkForUniqueness(dto.get("email"))) {
            return ResponseEntity.ok(new ResponseDto("Email is already taken", HttpStatus.CONFLICT));
        } else {
            return ResponseEntity.ok(new ResponseDto("Email is unique", HttpStatus.OK));
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public ResponseEntity<?> signUpNewUser(@Valid @RequestBody NewOwnerDto dto) {
        usersService.signUp(dto);
        return ResponseEntity.ok(new ResponseDto("Successfully added user", HttpStatus.OK));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activate(@PathVariable String code) {
        boolean isActivated = usersService.activateUser(code);
        if (isActivated) {
            return ResponseEntity.ok(new ResponseDto("User successfully activated", HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ResponseDto("User is not found", HttpStatus.NOT_FOUND));
        }
    }
}
