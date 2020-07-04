package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ru.itis.dogger.dto.comments.CommentDto;
import ru.itis.dogger.dto.comments.NewCommentDto;
import ru.itis.dogger.dto.places.DetailedPlaceDto;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.dto.places.SimplePlaceDto;
import ru.itis.dogger.models.place.Comment;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.models.place.Place;
import ru.itis.dogger.services.PlacesService;
import ru.itis.dogger.services.UsersService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<SimplePlaceDto> dtos = places.stream().map(SimplePlaceDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/addPlace")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addPlace(@RequestBody NewPlaceDto placeDto, Authentication authentication) {
        Optional<Owner> currentUser = usersService.getCurrentUser(authentication);
        Place newPlace = placesService.addPlace(placeDto, currentUser.get());
        return ResponseEntity.ok(DetailedPlaceDto.from(newPlace));
    }

    @GetMapping("/places/{placeId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getPlacePageInfo(@PathVariable Long placeId) {
        Optional<Place> place = placesService.getPlaceById(placeId);
        if (place.isPresent()) {
            return ResponseEntity.ok(DetailedPlaceDto.from(place.get()));
        } else
            return new ResponseEntity<>("There is no place with such id", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/places/{placeId}/addComment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> leaveComment(@PathVariable Long placeId, @RequestBody NewCommentDto dto,
                                          Authentication authentication) {
        Optional<Owner> currentUser = usersService.getCurrentUser(authentication);
        Comment savedComment = placesService.addComment(currentUser.get(), dto, placeId);
        if (savedComment != null) {
            return ResponseEntity.ok(CommentDto.from(savedComment));
        } else {
            return new ResponseEntity<>("Comment has not been added", HttpStatus.BAD_REQUEST);
        }
    }
}
