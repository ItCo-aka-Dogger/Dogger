package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.Place;

@Repository
public interface PlacesRepository extends JpaRepository<Place, Long> {
}
