package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.Meeting;

import java.util.List;

@Repository
public interface MeetingsRepository extends JpaRepository<Meeting, Long> {

    @Query("select m from Meeting m where m.date > CURRENT_TIMESTAMP")
    List<Meeting> findAllFutureMeetings();
}
