package ru.itis.dogger.services;

import ru.itis.dogger.dto.MeetingDto;
import ru.itis.dogger.models.Owner;

public interface MeetingsService {
    void addMeeting(MeetingDto dto, Owner creator);
}
