package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ru.itis.dogger.dto.reviews.ReviewDto;
import ru.itis.dogger.dto.reviews.NewReviewDto;
import ru.itis.dogger.dto.places.DetailedPlaceDto;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.dto.places.SimplePlaceDto;
import ru.itis.dogger.models.place.Review;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.models.place.Place;
import ru.itis.dogger.security.details.UserDetailsImpl;
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
        Owner currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Place newPlace = placesService.addPlace(placeDto, currentUser);
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

    @PostMapping("/places/{placeId}/addReview")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> leaveReview(@PathVariable Long placeId, @RequestBody NewReviewDto dto,
                                         Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Review savedReview = placesService.addReview(currentUser, dto, placeId);
        if (savedReview != null) {
            return ResponseEntity.ok(ReviewDto.from(savedReview));
        } else {
            return new ResponseEntity<>("Review has not been added", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/places/{placeId}/addFavorite")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addFavoritePlace(@PathVariable Long placeId, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Place> place = placesService.getPlaceById(placeId);
        if (!place.isPresent()) {
            return new ResponseEntity<>("There is no place with such id", HttpStatus.NOT_FOUND);
        }
        boolean favoriteExist = currentUser.getFavoritePlaces().stream().map(Place::getId).anyMatch(f -> f.equals(placeId));
        if (favoriteExist) {
            return new ResponseEntity<>("Already in favorite", HttpStatus.BAD_REQUEST);
        }
        usersService.addPlaceToFavorites(place.get(), currentUser);
        return new ResponseEntity<>("Successfully added place to favorites", HttpStatus.OK);
    }

    @PostMapping("/places/{placeId}/removeFavorite")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> removeFavoritePlace(@PathVariable Long placeId, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Place> place = placesService.getPlaceById(placeId);
        if (!place.isPresent()) {
            return new ResponseEntity<>("There is no place with such id", HttpStatus.NOT_FOUND);
        }
        boolean favoriteExist = currentUser.getFavoritePlaces().stream().map(Place::getId).anyMatch(f -> f.equals(placeId));
        if (!favoriteExist) {
            return new ResponseEntity<>("There is no such favorite for this user", HttpStatus.BAD_REQUEST);
        }
        usersService.removePlaceFromFavorites(place.get(), currentUser);
        return new ResponseEntity<>("Successfully removed place from favorites", HttpStatus.OK);
    }
}
