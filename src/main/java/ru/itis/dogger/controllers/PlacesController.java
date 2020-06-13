package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dogger.models.Place;
import ru.itis.dogger.services.PlacesService;

import java.util.List;

@RestController
public class PlacesController {

    private PlacesService placesService;

    @Autowired
    public PlacesController(PlacesService placesService) {
        this.placesService = placesService;
    }

    @GetMapping("/places/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllPlaces() {
        List<Place> places = placesService.getAllPlaces();
        return ResponseEntity.ok(places);
    }
}
