package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.dto.ResponseDto;
import ru.itis.dogger.dto.dogs.NewDogDto;
import ru.itis.dogger.models.owner.Dog;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.DogsService;

import java.util.Optional;

@RestController
public class DogsController {

    private DogsService dogsService;

    @Autowired
    public DogsController(DogsService dogsService) {
        this.dogsService = dogsService;
    }

    @PostMapping("/addDog")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addDog(@RequestBody NewDogDto dto, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        dogsService.addDog(dto, currentUser);
        return ResponseEntity.ok(new ResponseDto("Dog was successfully added", HttpStatus.OK));
    }

    @PostMapping("/editDog")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editDog(@RequestParam("dogId") Long dogId, @RequestBody NewDogDto dto,
                                     Authentication authentication) {
        Owner owner = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Dog> dog = dogsService.getDogById(dogId);
        if (!dog.isPresent()) {
            return ResponseEntity.ok(new ResponseDto("There is no dog with such id", HttpStatus.NOT_FOUND));
        }
        if (dog.get().getOwner().getId().equals(owner.getId())) {
            Dog editedDog = dogsService.editDog(dto, dogId);
            return ResponseEntity.ok(editedDog);
        } else {
            return ResponseEntity.ok(new ResponseDto("User does not have rights to edit this dog", HttpStatus.FORBIDDEN));
        }
    }

    @PostMapping("/deleteDog")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteDog(@RequestParam("dogId") Long dogId, Authentication authentication) {
        Owner owner = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Dog> dog = dogsService.getDogById(dogId);
        if (!dog.isPresent()) {
            return ResponseEntity.ok(new ResponseDto("There is no dog with such id", HttpStatus.NOT_FOUND));
        }
        if (!dog.get().getOwner().getId().equals(owner.getId())) {
            return ResponseEntity.ok(new ResponseDto("User does not have rights for deleting this dog", HttpStatus.FORBIDDEN));
        } else {
            dogsService.deleteDog(dogId);
            if (dogsService.getDogById(dogId).isPresent()) {
                return ResponseEntity.ok(new ResponseDto("Unexpected error - dog was not deleted", HttpStatus.BAD_REQUEST));
            } else
                return ResponseEntity.ok(new ResponseDto("Dog was successfully deleted", HttpStatus.OK));
        }
    }
}
