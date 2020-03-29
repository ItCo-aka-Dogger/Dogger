package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.dto.OwnerDto;
import ru.itis.dogger.services.UsersService;

import javax.validation.Valid;

@RestController
public class SignUpController {

    private UsersService usersService;

    @Autowired
    public SignUpController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signUp")
    @PreAuthorize("permitAll()")
    public void signUpNewUser(@Valid @RequestBody OwnerDto dto) {
        usersService.signUp(dto);
    }
}
