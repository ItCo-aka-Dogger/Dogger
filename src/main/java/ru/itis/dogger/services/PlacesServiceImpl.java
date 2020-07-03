package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.enums.Contact;
import ru.itis.dogger.models.place.Amenity;
import ru.itis.dogger.models.place.Comment;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.models.place.Place;
import ru.itis.dogger.models.place.PlaceType;
import ru.itis.dogger.repositories.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlacesServiceImpl implements PlacesService {

    private PlacesRepository placesRepository;
    private CommentsRepository commentsRepository;
    private TimecardsRepository timecardsRepository;
    private PlaceTypesRepository placeTypesRepository;
    private AmenitiesRepository amenitiesRepository;

    @Autowired
    public PlacesServiceImpl(PlacesRepository placesRepository, CommentsRepository commentsRepository,
                             TimecardsRepository timecardsRepository, PlaceTypesRepository placeTypesRepository,
                             AmenitiesRepository amenitiesRepository) {
        this.placesRepository = placesRepository;
        this.commentsRepository = commentsRepository;
        this.timecardsRepository = timecardsRepository;
        this.placeTypesRepository = placeTypesRepository;
        this.amenitiesRepository = amenitiesRepository;
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
        newPlace.setLongitude(placeDto.getLongitude());
        newPlace.setLatitude(placeDto.getLatitude());
        newPlace.setCreator(creator);

        PlaceType type = placeTypesRepository.findById(Long.parseLong(placeDto.getTypeId())).get();
        newPlace.setType(type);

        Map<Contact, String> contacts = new HashMap<>();
        for (Map.Entry<String, String> e : placeDto.getContacts().entrySet()) {
            contacts.put(Contact.valueOf(e.getKey().toUpperCase()), e.getValue());
        }
        newPlace.setContacts(contacts);

        List<Amenity> amenities = placeDto.getAmenitiesIds().stream()
                .map(amenityId -> amenitiesRepository.findById(Long.parseLong(amenityId)).get())
                .collect(Collectors.toList());
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

    @Override
    public List<PlaceType> getAllPlacesTypes(){
        return placeTypesRepository.findAll();
    }

    @Override
    public List<Amenity> getAllPlaceAmenities(){
        return amenitiesRepository.findAll();
    }
}
