package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.NewMeetingDto;
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
    public Meeting addMeeting(NewMeetingDto meetingForm, Owner creator) {
        Meeting newMeeting = new Meeting();

        newMeeting.setCoordinateX(meetingForm.getCoordinateX());
        newMeeting.setCoordinateY(meetingForm.getCoordinateY());
        newMeeting.setDate(meetingForm.getDate());
        newMeeting.setDescription(meetingForm.getDescription());
        newMeeting.setName(meetingForm.getName());
        newMeeting.setCreator(creator);
        newMeeting.setParticipants(Collections.singletonList(creator));

        return meetingsRepository.save(newMeeting);
    }

    @Override
    public List<Meeting> getAllFutureMeetings() {
        return meetingsRepository.findAllFutureMeetings();
    }

    @Override
    public Optional<Meeting> getMeetingById(Long id) {
        return meetingsRepository.findById(id);
    }

    @Override
    public boolean joinMeeting(Owner currentUser, Meeting meeting) {
        boolean isAlreadyJoined = currentUser.getMeetings().stream()
                .anyMatch(m -> m.getId().equals(meeting.getId()));
        if (!isAlreadyJoined) {
            meeting.getParticipants().add(currentUser);
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
            meeting.getParticipants().remove(currentUser);
            if (meeting.getParticipants().size() == 0) {
                meetingsRepository.deleteMeetingById(meeting.getId());
            } else {
                meetingsRepository.save(meeting);
            }
            return true;
        }
        return false;
    }

    @Override
    public Meeting editMeeting(NewMeetingDto dto, Owner owner, Long meetingId) {
        Meeting meeting = meetingsRepository.getOne(meetingId);
        meeting.setName(dto.getName());
        meeting.setDescription(dto.getDescription());
        meeting.setCoordinateX(dto.getCoordinateX());
        meeting.setCoordinateY(dto.getCoordinateY());
        meeting.setDate(dto.getDate());
        return meetingsRepository.save(meeting);
    }

}

