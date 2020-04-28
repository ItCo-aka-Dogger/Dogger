package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.MeetingDto;
import ru.itis.dogger.dto.NewMeetingForm;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.MeetingsService;

import java.util.List;
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
    public ResponseEntity<?> addMeeting(@RequestBody NewMeetingForm meetingForm, @RequestHeader(name = "Authorization") String token,
                                        Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        meetingsService.addMeeting(meetingForm, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meetings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMeetingsList(@RequestHeader(name = "Authorization") String token, Authentication authentication) {
        List<MeetingDto> meetingDtos = meetingsService.getAllMeetings().stream().map(mtg -> MeetingDto.from(mtg)).collect(Collectors.toList());
        return ResponseEntity.ok(meetingDtos);
    }

}
