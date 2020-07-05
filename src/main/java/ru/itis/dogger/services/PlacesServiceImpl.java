package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.itis.dogger.dto.reviews.NewReviewDto;
import ru.itis.dogger.dto.NewContactDto;
import ru.itis.dogger.dto.places.NewPlaceDto;
import ru.itis.dogger.models.contacts.PlaceContact;
import ru.itis.dogger.models.place.*;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.repositories.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlacesServiceImpl implements PlacesService {

    private PlacesRepository placesRepository;
    private ReviewsRepository reviewsRepository;
    private TimecardsRepository timecardsRepository;
    private PlaceTypesRepository placeTypesRepository;
    private AmenitiesRepository amenitiesRepository;
    private ContactTypesRepository contactTypesRepository;
    private PlaceContactsRepository placeContactsRepository;

    @Autowired
    public PlacesServiceImpl(PlacesRepository placesRepository, ReviewsRepository reviewsRepository,
                             TimecardsRepository timecardsRepository, PlaceTypesRepository placeTypesRepository,
                             AmenitiesRepository amenitiesRepository, ContactTypesRepository contactTypesRepository,
                             PlaceContactsRepository placeContactsRepository) {
        this.placesRepository = placesRepository;
        this.reviewsRepository = reviewsRepository;
        this.timecardsRepository = timecardsRepository;
        this.placeTypesRepository = placeTypesRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.contactTypesRepository = contactTypesRepository;
        this.placeContactsRepository = placeContactsRepository;
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

        PlaceType type = placeTypesRepository.findById(placeDto.getTypeId()).get();
        newPlace.setType(type);

        List<Amenity> amenities = placeDto.getAmenitiesIds().stream()
                .map(amenityId -> amenitiesRepository.findById(amenityId).get())
                .collect(Collectors.toList());
        newPlace.setAmenities(amenities);

        timecardsRepository.save(placeDto.getTimecard());
        newPlace.setTimecard(placeDto.getTimecard());

        Place savedPlace = placesRepository.save(newPlace);

        List<PlaceContact> contacts = new ArrayList<>();
        for (NewContactDto dto : placeDto.getContacts()) {
            PlaceContact contact = new PlaceContact();
            contact.setType(contactTypesRepository.findById(dto.getTypeId()).get());
            contact.setValue(dto.getValue());
            contact.setPlace(savedPlace);
            placeContactsRepository.save(contact);
            contacts.add(contact);
        }
        savedPlace.setContacts(contacts);

        return placesRepository.save(savedPlace);
    }

    @Override
    public Optional<Place> getPlaceById(Long placeId) {
        return placesRepository.findById(placeId);
    }

    @Override
    public Review addReview (Owner currentUser, NewReviewDto dto, Long placeId) {
        if (StringUtils.isEmpty(dto.getScore()))
            return null;
        Optional<Place> place = placesRepository.findById(placeId);
        if (place.isPresent()) {
            Review newReview = new Review();
            newReview.setComment(dto.getComment());
            newReview.setScore(dto.getScore());
            newReview.setAttachments(dto.getAttachments());
            newReview.setAuthor(currentUser);
            newReview.setDate(new Timestamp(System.currentTimeMillis()));
            newReview.setPlace(place.get());
            return reviewsRepository.save(newReview);
        } else {
            return null;
        }
    }
}
