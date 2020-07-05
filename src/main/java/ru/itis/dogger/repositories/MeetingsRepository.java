package ru.itis.dogger.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.meeting.Meeting;

import java.util.Date;
import java.util.List;

@Repository
public interface MeetingsRepository extends MongoRepository<Meeting, String> {

    List<Meeting> findByDateAfter(Date date);
}
