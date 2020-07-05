package ru.itis.dogger.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.Dog;

@Repository
public interface DogsRepository extends MongoRepository<Dog, String> {

}
