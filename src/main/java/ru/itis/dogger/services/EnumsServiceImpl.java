package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.models.place.Amenity;
import ru.itis.dogger.models.contacts.ContactType;
import ru.itis.dogger.models.place.PlaceType;
import ru.itis.dogger.repositories.AmenitiesRepository;
import ru.itis.dogger.repositories.ContactTypesRepository;
import ru.itis.dogger.repositories.PlaceTypesRepository;

import java.util.List;

@Service
public class EnumsServiceImpl implements EnumsService {

    private ContactTypesRepository contactTypesRepository;
    private PlaceTypesRepository placeTypesRepository;
    private AmenitiesRepository amenitiesRepository;

    @Autowired
    public EnumsServiceImpl(ContactTypesRepository contactTypesRepository, PlaceTypesRepository placeTypesRepository,
                            AmenitiesRepository amenitiesRepository) {
        this.contactTypesRepository = contactTypesRepository;
        this.placeTypesRepository = placeTypesRepository;
        this.amenitiesRepository = amenitiesRepository;
    }

    @Override
    public List<ContactType> getAllContactsTypes() {
        return contactTypesRepository.findAll();
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
