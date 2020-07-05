package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dogger.models.place.Timecard;

public interface TimecardsRepository extends JpaRepository<Timecard, Long> {
}
