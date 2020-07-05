package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.place.Amenity;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenity, Long> {
}
