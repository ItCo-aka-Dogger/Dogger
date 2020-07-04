package ru.itis.dogger.services;

import ru.itis.dogger.dto.comments.NewCommentDto;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.models.place.*;
import ru.itis.dogger.models.owner.Owner;

import java.util.List;
import java.util.Optional;

public interface PlacesService {
    List<Place> getAllPlaces();

    Place addPlace(NewPlaceDto placeDto, Owner owner);

    Optional<Place> getPlaceById(Long placeId);

    Comment addComment(Owner currentUser, NewCommentDto dto, Long placeId);
}
