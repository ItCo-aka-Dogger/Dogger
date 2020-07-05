package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.dto.reviews.NewReviewDto;
import ru.itis.dogger.enums.ContactType;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.models.place.*;
import ru.itis.dogger.repositories.PlacesRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        newPlace.setAddress(placeDto.getAddress());
        newPlace.setPhotoPath(placeDto.getPhotoPath());
        newPlace.setType(PlaceType.valueOf(placeDto.getPlaceType()));
        newPlace.setLongitude(placeDto.getLongitude());
        newPlace.setLatitude(placeDto.getLatitude());
        newPlace.setCreatorId(creator.getId());
        newPlace.setTimecard(placeDto.getTimecard());

        List<PlaceContact> contacts = new ArrayList<>();
        for (Map.Entry<String, String> e : placeDto.getContacts().entrySet()) {
            contacts.add(new PlaceContact(ContactType.valueOf(e.getKey().toUpperCase()), e.getValue()));
        }
        newPlace.setContacts(contacts);

        List<AmenityForDog> amenities = placeDto.getAmenities().stream()
                .map(amenityStr -> AmenityForDog.valueOf(amenityStr.toUpperCase())).collect(Collectors.toList());
        newPlace.setAmenities(amenities);
        return placesRepository.save(newPlace);
    }

    @Override
    public Optional<Place> getPlaceById(String placeId) {
        return placesRepository.findById(placeId);
    }

    @Override
    public Review addReview(Owner currentUser, NewReviewDto dto, String placeId) {
        Optional<Place> place = placesRepository.findById(placeId);
        if (place.isPresent()) {
            Review newReview = new Review();
            newReview.setComment(dto.getComment());
            newReview.setScore(dto.getScore());
            // TODO:
//            newReview.setAuthor(currentUser.getId());
            newReview.setDate(new Timestamp(System.currentTimeMillis()));
            place.get().addReview(newReview);
            placesRepository.save(place.get());
            return newReview;
        } else {
            return null;
        }
    }

    @Override
    public List<AmenityForDog> getAllAmenities() {
        // TODO:
        return null;
    }
}
