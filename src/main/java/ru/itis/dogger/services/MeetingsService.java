package ru.itis.dogger.services;

import ru.itis.dogger.forms.NewMeetingForm;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;

import java.util.List;
import java.util.Optional;


public interface MeetingsService {
    void addMeeting(NewMeetingForm form, Owner creator);

    List<Meeting> getAllMeetings();

    Optional<Meeting> getMeetingById(Long id);

    boolean joinMeeting(Owner currentUser, Long meetingId);
}
