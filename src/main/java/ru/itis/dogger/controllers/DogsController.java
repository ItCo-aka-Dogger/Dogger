package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.dto.dogs.DogDto;
import ru.itis.dogger.dto.dogs.EditDogDto;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.DogsService;

import java.util.List;

@RestController
public class DogsController {

    private DogsService dogsService;

    @Autowired
    public DogsController(DogsService dogsService) {
        this.dogsService = dogsService;
    }

    @PostMapping("/addDog")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addDog(@RequestBody DogDto dto, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        dogsService.addDog(dto, currentUser);
        return new ResponseEntity<>("Dog was successfully added", HttpStatus.OK);
    }

    @PostMapping("/editDogs")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editDog(@RequestBody List<EditDogDto> dtos) {
        if (dtos.isEmpty()) {
            return new ResponseEntity<>("No changes in dogs", HttpStatus.OK);
        }
        dogsService.editDogs(dtos);
        return new ResponseEntity<>("Dogs info was successfully updated", HttpStatus.OK);
    }
}
