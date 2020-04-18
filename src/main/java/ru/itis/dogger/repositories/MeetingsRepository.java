package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.Meeting;

@Repository
public interface MeetingsRepository extends JpaRepository<Meeting, Long> {
}
