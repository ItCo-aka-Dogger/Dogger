package ru.itis.dogger.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.place.AmenityForDog;
import ru.itis.dogger.models.place.Place;

import java.util.List;

@Repository
public interface PlacesRepository extends MongoRepository<Place, String> {

    @Query("{ 'firstname' : ?0 }")
    List<AmenityForDog> getAllAmenities();
}
