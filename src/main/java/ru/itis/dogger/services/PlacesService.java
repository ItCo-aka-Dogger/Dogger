package ru.itis.dogger.services;

import ru.itis.dogger.dto.reviews.NewReviewDto;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.models.place.*;
import ru.itis.dogger.models.owner.Owner;

import java.util.List;
import java.util.Optional;

public interface PlacesService {
    List<Place> getAllPlaces();

    Place addPlace(NewPlaceDto placeDto, Owner owner);

    Optional<Place> getPlaceById(Long placeId);

    Review addReview(Owner currentUser, NewReviewDto dto, Long placeId);
}
