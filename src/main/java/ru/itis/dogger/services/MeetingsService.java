package ru.itis.dogger.services;

import ru.itis.dogger.dto.NewMeetingForm;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;

import java.util.List;


public interface MeetingsService {
    void addMeeting(NewMeetingForm form, Owner creator);

    List<Meeting> getAllMeetings();
}
