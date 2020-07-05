package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.meetings.NewMeetingDto;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.MeetingsRepository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
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
    public Meeting addMeeting(NewMeetingDto meetingForm, Owner creator) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (meetingForm.getDate().before(now)) {
            return null;
        }
        Meeting newMeeting = new Meeting();

        newMeeting.setLongitude(meetingForm.getLongitude());
        newMeeting.setLatitude(meetingForm.getLatitude());
        newMeeting.setDate(meetingForm.getDate());
        newMeeting.setDescription(meetingForm.getDescription());
        newMeeting.setName(meetingForm.getName());
        newMeeting.setCreatorId(creator.getId());
        newMeeting.setParticipantsIds(Collections.singletonList(creator.getId()));

        return meetingsRepository.save(newMeeting);
    }

    @Override
    public List<Meeting> getAllFutureMeetings() {
        return meetingsRepository.findByDateAfter(new Date());
    }

    @Override
    public Optional<Meeting> getMeetingById(String id) {
        return meetingsRepository.findById(id);
    }

    @Override
    public boolean joinMeeting(Owner currentUser, Meeting meeting) {
        boolean isAlreadyJoined = currentUser.getMeetings().stream()
                .anyMatch(m -> m.getId().equals(meeting.getId()));
        if (!isAlreadyJoined) {
            meeting.getParticipantsIds().add(currentUser.getId());
            meetingsRepository.save(meeting);
            return true;
        }
        return false;
    }

    @Override
    public boolean unjoinMeeting(Owner currentUser, Meeting meeting) {
        boolean isJoined = currentUser.getMeetings().stream()
                .anyMatch(m -> m.getId().equals(meeting.getId()));
        if (isJoined) {
            meeting.getParticipantsIds().remove(currentUser.getId());
            if (meeting.getParticipantsIds().isEmpty()) {
                meetingsRepository.deleteById(meeting.getId());
            } else {
                meetingsRepository.save(meeting);
            }
            return true;
        }
        return false;
    }

    @Override
    public Meeting editMeeting(NewMeetingDto dto, Owner owner, String meetingId) {
        Meeting meeting = meetingsRepository.findById(meetingId).orElse(new Meeting());
        meeting.setName(dto.getName());
        meeting.setDescription(dto.getDescription());
        meeting.setLongitude(dto.getLongitude());
        meeting.setLatitude(dto.getLatitude());
        meeting.setDate(dto.getDate());
        return meetingsRepository.save(meeting);
    }

}

