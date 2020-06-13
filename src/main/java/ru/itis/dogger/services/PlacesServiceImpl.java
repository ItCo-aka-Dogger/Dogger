package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.models.Place;
import ru.itis.dogger.repositories.PlacesRepository;

import java.util.List;

@Service
public class PlacesServiceImpl implements PlacesService {

    private PlacesRepository placesRepository;

    @Autowired
    public PlacesServiceImpl(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    @Override
    public List<Place> getAllPlaces() {
        return placesRepository.findAll();
    }
}
