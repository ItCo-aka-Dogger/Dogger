package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dogger.models.place.PlaceContact;

public interface PlaceContactsRepository extends JpaRepository<PlaceContact, Long> {
}
