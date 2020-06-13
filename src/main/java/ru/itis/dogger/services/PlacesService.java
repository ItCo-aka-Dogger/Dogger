package ru.itis.dogger.services;

import ru.itis.dogger.dto.NewPlaceDto;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.models.Place;

import java.util.List;

public interface PlacesService {
    List<Place> getAllPlaces();

    Place addPlace(NewPlaceDto placeDto, Owner owner);
}
