package ru.itis.dogger.services;

import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.models.Comment;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.models.Place;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlacesService {
    List<Place> getAllPlaces();

    Place addPlace(NewPlaceDto placeDto, Owner owner);

    Optional<Place> getPlaceById(Long placeId);

    Comment addComment(Owner currentUser, Map<String, String> dto, Long placeId);
}
