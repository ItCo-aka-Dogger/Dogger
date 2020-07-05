package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.services.EnumsService;

@RestController
public class EnumsController {

    private EnumsService enumsService;

    @Autowired
    public EnumsController(EnumsService enumsService) {
        this.enumsService = enumsService;
    }

    @GetMapping("/amenities")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllAmenitiesList() {
        return ResponseEntity.ok(enumsService.getAllPlaceAmenities());
    }

    @GetMapping("/placeTypes")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllTypesOfPlaces() {
        return ResponseEntity.ok(enumsService.getAllPlacesTypes());
    }

    @GetMapping("/contactTypes")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllTypesOfContacts() {
        return ResponseEntity.ok(enumsService.getAllContactsTypes());
    }
}
