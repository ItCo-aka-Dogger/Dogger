package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dogger.models.place.Amenity;

public interface AmenitiesRepository extends JpaRepository<Amenity, Long> {
}
