package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.contacts.ContactType;

@Repository
public interface ContactTypesRepository extends JpaRepository<ContactType, Long> {
}
