package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dogger.models.ContactType;

public interface ContactTypesRepository extends JpaRepository<ContactType, Long> {
}
