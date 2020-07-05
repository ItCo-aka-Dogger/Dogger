package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dogger.models.place.PlaceType;

public interface PlaceTypesRepository extends JpaRepository<PlaceType, Long> {
}
