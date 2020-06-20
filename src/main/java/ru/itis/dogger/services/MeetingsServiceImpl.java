package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.NewMeetingDto;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.MeetingsRepository;
import ru.itis.dogger.repositories.UsersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingsServiceImpl implements MeetingsService {

    private MeetingsRepository meetingsRepository;
    private UsersRepository usersRepository;

    @Autowired
    public MeetingsServiceImpl(MeetingsRepository meetingsRepository, UsersRepository usersRepository) {
        this.meetingsRepository = meetingsRepository;
        this.usersRepository = usersRepository;
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
    public boolean joinMeeting(Owner currentUser, Long meetingId) {
        Optional<Meeting> meeting = meetingsRepository.findById(meetingId);
        boolean isAlreadyJoined = currentUser.getMeetings().stream()
                .anyMatch(m -> m.getId().equals(meetingId));

        if (!isAlreadyJoined && meeting.isPresent()) {
            meeting.get().getParticipants().add(currentUser);
            meetingsRepository.save(meeting.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean unjoinMeeting(Owner currentUser, Long meetingId) {
        Optional<Meeting> meeting = meetingsRepository.findById(meetingId);
        boolean isJoined = currentUser.getMeetings().stream()
                .anyMatch(m -> m.getId().equals(meetingId));
        if (isJoined && meeting.isPresent()) {
            meeting.get().getParticipants().remove(currentUser);
            if (meeting.get().getParticipants().size() == 0) {
                meetingsRepository.deleteMeetingById(meetingId);
            } else {
                meetingsRepository.save(meeting.get());
            }
            return true;
        }
        return false;
    }

}

