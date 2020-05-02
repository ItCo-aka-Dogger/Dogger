package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.DetailedMeetingDto;
import ru.itis.dogger.dto.SimpleMeetingDto;
import ru.itis.dogger.dto.NewMeetingDto;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.MeetingsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MeetingsController {

    private MeetingsService meetingsService;

    @Autowired
    public MeetingsController(MeetingsService meetingsService) {
        this.meetingsService = meetingsService;
    }

    @PostMapping("/addMeeting")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addMeeting(@RequestBody NewMeetingDto meetingForm, @RequestHeader(name = "Authorization") String token,
                                        Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        meetingsService.addMeeting(meetingForm, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meetings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMeetingsList(@RequestHeader(name = "Authorization") String token, Authentication authentication) {
        List<SimpleMeetingDto> meetingDtos = meetingsService.getAllMeetings().stream().map(mtg -> SimpleMeetingDto.from(mtg)).collect(Collectors.toList());
        return ResponseEntity.ok(meetingDtos);
    }

    @GetMapping("/meetings/{meetingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getDetailedMeeting(@RequestHeader(name = "Authorization") String token, @PathVariable Long meetingId) {
        Optional<Meeting> meeting = meetingsService.getMeetingById(meetingId);
        if (meeting.isPresent()) {
            return ResponseEntity.ok(DetailedMeetingDto.from(meeting.get()));
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/meetings/{meetingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> joinMeeting(@RequestHeader(name = "Authorization") String token,
                                         @PathVariable Long meetingId, Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        boolean isJoined = meetingsService.joinMeeting(currentUser, meetingId);
        if (isJoined) {
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

}
