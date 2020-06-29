package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dogger.models.Timecard;

public interface TimecardsRepository extends JpaRepository<Timecard, Long> {
}
