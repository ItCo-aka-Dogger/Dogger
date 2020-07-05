package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.dto.TokenDto;
import ru.itis.dogger.dto.owner.NewOwnerDto;
import ru.itis.dogger.enums.TokenStatus;
import ru.itis.dogger.services.UsersService;

@RestController
public class LoginController {

    private UsersService usersService;

    @Autowired
    public LoginController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> login(@RequestBody NewOwnerDto dto) {
        TokenDto token = usersService.login(dto);
        if (token.getStatus() == TokenStatus.INVALID) {
            return new ResponseEntity<>(token, HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(token);
        }
    }
}
