package ru.itis.dogger.services;

import ru.itis.dogger.dto.meetings.NewMeetingDto;
import ru.itis.dogger.models.meeting.Meeting;
import ru.itis.dogger.models.owner.Owner;

import java.util.List;
import java.util.Optional;


public interface MeetingsService {
    Meeting addMeeting(NewMeetingDto form, Owner creator);

    List<Meeting> getAllFutureMeetings();

    Optional<Meeting> getMeetingById(Long id);

    boolean joinMeeting(Owner currentUser, Meeting meeting);

    boolean unjoinMeeting(Owner currentUser, Meeting meeting);

    Meeting editMeeting(NewMeetingDto dto, Long meetingId);
}
