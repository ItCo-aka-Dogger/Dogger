package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.enums.AmenityForDog;
import ru.itis.dogger.enums.ContactType;
import ru.itis.dogger.enums.PlaceType;
import ru.itis.dogger.models.Comment;
import ru.itis.dogger.models.Contact;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.models.Place;
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
        newPlace.setCreator(creator.getId());
        newPlace.setTimecard(placeDto.getTimecard());

        List<Contact> contacts = new ArrayList<>();
        for (Map.Entry<String, String> e : placeDto.getContacts().entrySet()) {
            contacts.add(new Contact(ContactType.valueOf(e.getKey().toUpperCase()), e.getValue()));
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
    public Comment addComment(Owner currentUser, Map<String, String> dto, String placeId) {
        if (!dto.containsKey("rating"))
            return null;
        Optional<Place> place = placesRepository.findById(placeId);
        if (place.isPresent()) {
            Comment newComment = new Comment();
            if(dto.containsKey("text")){
                newComment.setText(dto.get("text"));
            }
            newComment.setRating(Integer.parseInt(dto.get("rating")));
            newComment.setAuthor(currentUser.getId());
            newComment.setDate(new Timestamp(System.currentTimeMillis()));

            place.get().addComment(newComment);
            placesRepository.save(place.get());
            return newComment;
        } else {
            return null;
        }
    }
}
