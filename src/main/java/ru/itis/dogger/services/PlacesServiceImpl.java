package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.NewPlaceDto;
import ru.itis.dogger.enums.AmenityForDog;
import ru.itis.dogger.enums.Contact;
import ru.itis.dogger.enums.PlaceType;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.models.Place;
import ru.itis.dogger.repositories.PlacesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Place addPlace(NewPlaceDto placeDto, Owner creator) {
        Place newPlace = new Place();

        newPlace.setName(placeDto.getName());
        newPlace.setDescription(placeDto.getDescription());
        newPlace.setPhoto_path(placeDto.getPhotoPath());
        newPlace.setType(PlaceType.valueOf(placeDto.getPlaceType()));
        newPlace.setCoordinateX(placeDto.getCoordinateX());
        newPlace.setCoordinateY(placeDto.getCoordinateY());
        newPlace.setCreator(creator);

        Map<Contact, String> contacts = new HashMap<>();
        for (Map.Entry<String, String> e : placeDto.getContacts().entrySet()){
            contacts.put(Contact.valueOf(e.getKey().toUpperCase()), e.getValue());
        }
        newPlace.setContacts(contacts);

        List<AmenityForDog> amenities = placeDto.getAmenities().stream()
                .map(amenityStr -> AmenityForDog.valueOf(amenityStr.toUpperCase())).collect(Collectors.toList());
        newPlace.setAmenities(amenities);

        return placesRepository.save(newPlace);
    }

    @Override
    public Optional<Place> getPlaceById(Long placeId) {
        return placesRepository.findById(placeId);
    }
}
