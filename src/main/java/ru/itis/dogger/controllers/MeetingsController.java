package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.ResponseDto;
import ru.itis.dogger.dto.meetings.DetailedMeetingDto;
import ru.itis.dogger.dto.meetings.NewMeetingDto;
import ru.itis.dogger.dto.meetings.SimpleMeetingDto;
import ru.itis.dogger.models.meeting.Meeting;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.MeetingsService;
import ru.itis.dogger.services.UsersService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MeetingsController {

    private MeetingsService meetingsService;
    private UsersService usersService;

    @Autowired
    public MeetingsController(MeetingsService meetingsService, UsersService usersService) {
        this.meetingsService = meetingsService;
        this.usersService = usersService;
    }

    @PostMapping("/addMeeting")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addMeeting(@RequestBody NewMeetingDto dto, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Meeting newMeeting = meetingsService.addMeeting(dto, currentUser);
        if (newMeeting == null) {
            return ResponseEntity.ok(new ResponseDto("Meeting was not added. Check meeting's date validation.",
                    HttpStatus.BAD_REQUEST));
        } else {
            return ResponseEntity.ok(DetailedMeetingDto.from(newMeeting));
        }
    }

    @GetMapping("/meetings/future")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllMeetings() {
        List<SimpleMeetingDto> meetingDtos = meetingsService.getAllFutureMeetings()
                .stream().map(SimpleMeetingDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(meetingDtos);
    }

    @GetMapping("/meetings/joined")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMeetingsInWhichUserParticipates(@RequestParam Long userId) {
        Optional<Owner> user = usersService.getUserById(userId);
        if (user.isPresent()) {
            List<SimpleMeetingDto> meetingDtos = user.get().getMeetings()
                    .stream().map(SimpleMeetingDto::from).collect(Collectors.toList());
            return ResponseEntity.ok(meetingDtos);
        } else {
            return ResponseEntity.ok(new ResponseDto("There is no user with such id", HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping("/meetings/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMeetingsCreatedByMe(Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        List<SimpleMeetingDto> meetingDtos = currentUser.getMyMeetings()
                .stream().map(SimpleMeetingDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(meetingDtos);
    }

    @GetMapping("/meetings/{meetingId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getDetailedMeeting(@PathVariable Long meetingId) {
        Optional<Meeting> meeting = meetingsService.getMeetingById(meetingId);
        if (meeting.isPresent()) {
            return ResponseEntity.ok(DetailedMeetingDto.from(meeting.get()));
        } else
            return ResponseEntity.ok(new ResponseDto("There is no meeting with such id", HttpStatus.NOT_FOUND));
    }

    @PostMapping("/editMeeting")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editMeeting(@RequestParam("meetingId") Long meetingId, @RequestBody NewMeetingDto dto,
                                         Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Meeting> meeting = meetingsService.getMeetingById(meetingId);
        if (!meeting.isPresent()) {
            return ResponseEntity.ok(new ResponseDto("There is no meeting with such id", HttpStatus.NOT_FOUND));
        }
        if (meeting.get().getCreator().equals(currentUser)) {
            Meeting newMeeting = meetingsService.editMeeting(dto, meetingId);
            return ResponseEntity.ok(DetailedMeetingDto.from(newMeeting));
        } else {
            return ResponseEntity.ok(new ResponseDto("User does not have rights to edit this meeting",
                    HttpStatus.FORBIDDEN));
        }
    }

    @PostMapping("/meetings/{meetingId}/join")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> joinMeeting(@PathVariable Long meetingId, Authentication authentication) {
        Owner currentUser = usersService.getCurrentUser(authentication).get();
        Optional<Meeting> meeting = meetingsService.getMeetingById(meetingId);
        if (meeting.isPresent()) {
            boolean isJoined = meetingsService.joinMeeting(currentUser, meeting.get());
            if (isJoined) {
                return ResponseEntity.ok(DetailedMeetingDto.from(meeting.get()));
            } else
                return ResponseEntity.ok(new ResponseDto("User has already joined meeting", HttpStatus.BAD_REQUEST));
        } else {
            return ResponseEntity.ok(new ResponseDto("There is no such meeting", HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping("/meetings/{meetingId}/unjoin")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> unjoinMeeting(@PathVariable Long meetingId, Authentication authentication) {
        Owner currentUser = usersService.getCurrentUser(authentication).get();
        Optional<Meeting> meeting = meetingsService.getMeetingById(meetingId);
        if (meeting.isPresent()) {
            boolean isUnjoined = meetingsService.unjoinMeeting(currentUser, meeting.get());
            if (isUnjoined) {
                return ResponseEntity.ok(DetailedMeetingDto.from(meeting.get()));
            } else {
                return ResponseEntity.ok(new ResponseDto("User is not participating in meeting",
                        HttpStatus.BAD_REQUEST));
            }
        } else {
            return ResponseEntity.ok(new ResponseDto("There is no such meeting", HttpStatus.NOT_FOUND));
        }
    }
}
