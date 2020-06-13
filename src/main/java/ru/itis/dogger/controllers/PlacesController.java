package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.DetailedMeetingDto;
import ru.itis.dogger.dto.NewPlaceDto;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.models.Place;
import ru.itis.dogger.services.PlacesService;
import ru.itis.dogger.services.UsersService;

import java.util.List;
import java.util.Optional;

@RestController
public class PlacesController {

    private PlacesService placesService;
    private UsersService usersService;

    @Autowired
    public PlacesController(PlacesService placesService, UsersService usersService) {
        this.placesService = placesService;
        this.usersService = usersService;
    }

    @GetMapping("/places/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllPlaces() {
        List<Place> places = placesService.getAllPlaces();
        return ResponseEntity.ok(places);
    }

    @PostMapping("/addPlace")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addPlace(@RequestBody NewPlaceDto placeDto, @RequestHeader(name = "Authorization") String token,
                                      Authentication authentication){
        Optional<Owner> currentUser = usersService.getCurrentUser(authentication);
        if (currentUser.isPresent()) {
            Place newPlace = placesService.addPlace(placeDto, currentUser.get());
            return ResponseEntity.ok(newPlace);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/places/{placeId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getPlacePageInfo(@PathVariable Long placeId) {
        Optional<Place> place = placesService.getPlaceById(placeId);
        if (place.isPresent()) {
            return ResponseEntity.ok(place);
        } else
            return ResponseEntity.notFound().build();
    }
}
