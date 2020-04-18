package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.MeetingDto;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.MeetingsRepository;

import java.util.Collections;

@Service
public class MeetingsServiceImpl implements MeetingsService {

    private MeetingsRepository meetingsRepository;

    @Autowired
    public MeetingsServiceImpl(MeetingsRepository meetingsRepository) {
        this.meetingsRepository = meetingsRepository;
    }

    @Override
    public void addMeeting(MeetingDto dto, Owner creator) {
        Meeting newMeeting = new Meeting();

        newMeeting.setCoordinateX(dto.getCoordinateX());
        newMeeting.setCoordinateY(dto.getCoordinateY());
        newMeeting.setDate(dto.getDate());
        newMeeting.setDescription(dto.getDescription());
        newMeeting.setName(dto.getName());

        newMeeting.setParticipants(Collections.singletonList(creator));

        meetingsRepository.save(newMeeting);
    }
}
