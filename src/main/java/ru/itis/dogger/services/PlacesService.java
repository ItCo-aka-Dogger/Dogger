package ru.itis.dogger.services;

import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.dto.reviews.NewReviewDto;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.models.place.AmenityForDog;
import ru.itis.dogger.models.place.Place;
import ru.itis.dogger.models.place.Review;

import java.util.List;
import java.util.Optional;

public interface PlacesService {
    List<Place> getAllPlaces();

    Place addPlace(NewPlaceDto placeDto, Owner owner);

    Optional<Place> getPlaceById(String placeId);

    Review addReview(Owner currentUser, NewReviewDto dto, String placeId);

    List<AmenityForDog> getAllAmenities();
}
