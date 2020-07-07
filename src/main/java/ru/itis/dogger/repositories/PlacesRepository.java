package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.place.Place;

import java.util.List;

@Repository
public interface PlacesRepository extends JpaRepository<Place, Long> {

    @Query(nativeQuery = true, value = "select * from place where place.verified = true")
    List<Place> getAllVerifiedPlaces();
}
