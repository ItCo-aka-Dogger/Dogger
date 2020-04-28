package ru.itis.dogger.services;

import ru.itis.dogger.dto.NewMeetingForm;
import ru.itis.dogger.models.Owner;


public interface MeetingsService {
    void addMeeting(NewMeetingForm form, Owner creator);

    String getAllMeetings();
}
