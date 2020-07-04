package ru.itis.dogger.services;
import ru.itis.dogger.models.place.Amenity;
import ru.itis.dogger.models.ContactType;
import ru.itis.dogger.models.place.PlaceType;

import java.util.List;

public interface EnumsService {
    List<ContactType> getAllContactsTypes();

    List<PlaceType> getAllPlacesTypes();

    List<Amenity> getAllPlaceAmenities();
}
