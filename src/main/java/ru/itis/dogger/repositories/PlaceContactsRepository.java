package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.contacts.PlaceContact;

@Repository
public interface PlaceContactsRepository extends JpaRepository<PlaceContact, Long> {
}
