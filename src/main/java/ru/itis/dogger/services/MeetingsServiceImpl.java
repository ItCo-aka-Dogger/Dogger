package ru.itis.dogger.services;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.NewMeetingForm;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.MeetingsRepository;

import java.util.Collections;
import java.util.List;

@Service
public class MeetingsServiceImpl implements MeetingsService {

    private MeetingsRepository meetingsRepository;

    @Autowired
    public MeetingsServiceImpl(MeetingsRepository meetingsRepository) {
        this.meetingsRepository = meetingsRepository;
    }

    @Override
    public void addMeeting(NewMeetingForm meetingForm, Owner creator) {
        Meeting newMeeting = new Meeting();

        newMeeting.setCoordinateX(meetingForm.getCoordinateX());
        newMeeting.setCoordinateY(meetingForm.getCoordinateY());
        newMeeting.setDate(meetingForm.getDate());
        newMeeting.setDescription(meetingForm.getDescription());
        newMeeting.setName(meetingForm.getName());
        newMeeting.setCreator(creator);
        newMeeting.setParticipants(Collections.singletonList(creator));

        meetingsRepository.save(newMeeting);
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingsRepository.findAll();
    }
}
