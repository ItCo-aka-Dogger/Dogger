package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.models.ContactType;
import ru.itis.dogger.models.place.AmenityForDog;
import ru.itis.dogger.models.place.PlaceType;
import ru.itis.dogger.services.PlacesService;

import java.util.List;

@RestController
public class EnumsController {

    private PlacesService placesService;

    @Autowired
    public EnumsController(PlacesService placesService) {
        this.placesService = placesService;
    }

    // TODO: implement aggregated collections

    @GetMapping("/amenities")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<AmenityForDog>> getAllAmenitiesList() {
        throw new UnsupportedOperationException();
//        return ResponseEntity.ok(placesService.getAllAmenities());
    }

    @GetMapping("/placeTypes")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<PlaceType>> getAllTypesOfPlaces() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/contactTypes")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ContactType>> getAllTypesOfContacts() {
        throw new UnsupportedOperationException();
    }
}
