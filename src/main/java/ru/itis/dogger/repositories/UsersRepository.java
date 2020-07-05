package ru.itis.dogger.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.Owner;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Owner, String> {

    Optional<Owner> findByActivationCode(String code);

    Optional<Owner> findByEmail(String email);
}
