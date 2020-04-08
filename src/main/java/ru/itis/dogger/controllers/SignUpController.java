package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
        if (!usersService.signUp(dto)) {
//            throw exception
            System.out.println("user already in the system");
        } else {
            System.out.println("added user");
        }

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/activate/{code}")
    public void activate(@PathVariable String code) {
        boolean isActivated = usersService.activateUser(code);

        if (isActivated) {
            System.out.println("User successfully activated");
        } else {
            System.out.println("Activation code is not found!");
        }

    }
}
