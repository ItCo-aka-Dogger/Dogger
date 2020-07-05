package ru.itis.dogger.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.place.Place;

@Repository
public interface PlacesRepository extends MongoRepository<Place, String> {

}
