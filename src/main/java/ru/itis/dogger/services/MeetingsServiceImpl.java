package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.forms.NewMeetingForm;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.MeetingsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Meeting> getMeetingById(long id) {
        return meetingsRepository.findById(id);
    }

    @Override
    public void joinMeeting(Owner currentUser, long meetingId) {
        boolean isAlreadyJoined = currentUser.getMeetings().stream()
                .anyMatch(m -> m.getId().equals(meetingId));
        if (!isAlreadyJoined) {
            Meeting meeting = meetingsRepository.findById(meetingId).orElseThrow(IllegalArgumentException::new);
            meeting.getParticipants().add(currentUser);
            meetingsRepository.save(meeting);
        }
    }
}
