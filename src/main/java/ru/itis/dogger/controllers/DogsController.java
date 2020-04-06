package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.dto.DogDto;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.DogsService;

@RestController
public class DogsController {

    private DogsService dogsService;

    @Autowired
    public DogsController(DogsService dogsService) {
        this.dogsService = dogsService;
    }

    @PostMapping("/addDog")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addDog(@RequestBody DogDto dto, @RequestHeader(name = "Authorization") String token,
                                    Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();

        dogsService.addDog(dto, currentUser);
        return ResponseEntity.ok().build();
    }
}
