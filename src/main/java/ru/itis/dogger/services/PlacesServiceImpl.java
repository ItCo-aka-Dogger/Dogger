package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.enums.AmenityForDog;
import ru.itis.dogger.enums.Contact;
import ru.itis.dogger.enums.PlaceType;
import ru.itis.dogger.models.Comment;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.models.Place;
import ru.itis.dogger.repositories.CommentsRepository;
import ru.itis.dogger.repositories.PlacesRepository;
import ru.itis.dogger.repositories.TimecardsRepository;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlacesServiceImpl implements PlacesService {

    private PlacesRepository placesRepository;
    private CommentsRepository commentsRepository;
    private TimecardsRepository timecardsRepository;

    @Autowired
    public PlacesServiceImpl(PlacesRepository placesRepository, CommentsRepository commentsRepository, TimecardsRepository timecardsRepository) {
        this.placesRepository = placesRepository;
        this.commentsRepository = commentsRepository;
        this.timecardsRepository = timecardsRepository;
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
        newPlace.setPhoto_path(placeDto.getPhotoPath());
        newPlace.setType(PlaceType.valueOf(placeDto.getPlaceType()));
        newPlace.setLongitude(placeDto.getLongitude());
        newPlace.setLatitude(placeDto.getLatitude());
        newPlace.setCreator(creator);

        Map<Contact, String> contacts = new HashMap<>();
        for (Map.Entry<String, String> e : placeDto.getContacts().entrySet()) {
            contacts.put(Contact.valueOf(e.getKey().toUpperCase()), e.getValue());
        }
        newPlace.setContacts(contacts);

        List<AmenityForDog> amenities = placeDto.getAmenities().stream()
                .map(amenityStr -> AmenityForDog.valueOf(amenityStr.toUpperCase())).collect(Collectors.toList());
        newPlace.setAmenities(amenities);
        timecardsRepository.save(placeDto.getTimecard());
        newPlace.setTimecard(placeDto.getTimecard());
        return placesRepository.save(newPlace);
    }

    @Override
    public Optional<Place> getPlaceById(Long placeId) {
        return placesRepository.findById(placeId);
    }

    @Override
    public Comment addComment(Owner currentUser, Map<String, String> dto, Long placeId) {
        if (!dto.containsKey("rating"))
            return null;
        Optional<Place> place = placesRepository.findById(placeId);
        if (place.isPresent()) {
            Comment newComment = new Comment();
            if(dto.containsKey("text")){
                newComment.setText(dto.get("text"));
            }
            newComment.setRating(Integer.parseInt(dto.get("rating")));
            newComment.setAuthor(currentUser);
            newComment.setDate(new Timestamp(System.currentTimeMillis()));
            newComment.setPlace(place.get());
            return commentsRepository.save(newComment);
        } else {
            return null;
        }
    }
}
