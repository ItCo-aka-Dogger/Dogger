package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.dogger.models.Meeting;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MeetingsRepository extends JpaRepository<Meeting, Long> {

    @Query("select m from Meeting m where m.date > CURRENT_TIMESTAMP")
    List<Meeting> findAllFutureMeetings();

    @Transactional
    @Modifying
    @Query("delete from Meeting m where m.id = :meetingId")
    void deleteMeetingById(@Param("meetingId") Long meetingId);
}
