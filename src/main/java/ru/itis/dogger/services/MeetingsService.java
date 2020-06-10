package ru.itis.dogger.services;

import ru.itis.dogger.dto.NewMeetingDto;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;

import java.util.List;
import java.util.Optional;


public interface MeetingsService {
    void addMeeting(NewMeetingDto form, Owner creator);

    List<Meeting> getAllMeetings();

    List<Meeting> getParticipatedMeetings(Long id);

    Optional<Meeting> getMeetingById(Long id);

    boolean joinMeeting(Owner currentUser, Long meetingId);
}
