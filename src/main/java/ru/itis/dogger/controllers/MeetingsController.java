package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.MeetingDto;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.security.details.UserDetailsImpl;
import ru.itis.dogger.services.MeetingsService;

@RestController
public class MeetingsController {

    private MeetingsService meetingsService;

    @Autowired
    public MeetingsController(MeetingsService meetingsService) {
        this.meetingsService = meetingsService;
    }

    @PostMapping("/addMeeting")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addMeeting(@RequestBody MeetingDto dto, @RequestHeader(name = "Authorization") String token,
                                        Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        meetingsService.addMeeting(dto, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meetings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMeetingsList(@RequestHeader(name = "Authorization") String token, Authentication authentication) {
        return ResponseEntity.ok(meetingsService.getAllMeetings());
    }

}
