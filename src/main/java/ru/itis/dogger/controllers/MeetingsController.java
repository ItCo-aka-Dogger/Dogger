package ru.itis.dogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dogger.dto.DetailedMeetingDto;
import ru.itis.dogger.dto.SimpleMeetingDto;
import ru.itis.dogger.dto.NewMeetingDto;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
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
    public ResponseEntity<?> addMeeting(@RequestBody NewMeetingDto dto, @RequestHeader(name = "Authorization") String token,
                                        Authentication authentication) {
        Optional<Owner> currentUser = usersService.getCurrentUser(authentication);
        if (currentUser.isPresent()) {
            Meeting newMeeting = meetingsService.addMeeting(dto, currentUser.get());
            return ResponseEntity.ok(DetailedMeetingDto.from(newMeeting));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    public ResponseEntity<?> getMeetingsInWhichUserParticipates(@RequestParam Long userId,
                                                                @RequestHeader(name = "Authorization") String token) {
        Optional<Owner> currentUser = usersService.getUserById(userId);
        if (currentUser.isPresent()){
            List<SimpleMeetingDto> meetingDtos = currentUser.get().getMeetings()
                    .stream().map(SimpleMeetingDto::from).collect(Collectors.toList());
            return ResponseEntity.ok(meetingDtos);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/meetings/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMeetingsCreatedByMe(@RequestHeader(name = "Authorization") String token,
                                                    Authentication authentication) {
        Optional<Owner> currentUser = usersService.getCurrentUser(authentication);
        if (currentUser.isPresent()) {
            List<SimpleMeetingDto> meetingDtos = currentUser.get().getMyMeetings()
                    .stream().map(SimpleMeetingDto::from).collect(Collectors.toList());
            return ResponseEntity.ok(meetingDtos);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/meetings/{meetingId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getDetailedMeeting(@PathVariable Long meetingId) {
        Optional<Meeting> meeting = meetingsService.getMeetingById(meetingId);
        if (meeting.isPresent()) {
            return ResponseEntity.ok(DetailedMeetingDto.from(meeting.get()));
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/meetings/{meetingId}/join")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> joinMeeting(@RequestHeader(name = "Authorization") String token,
                                         @PathVariable Long meetingId, Authentication authentication) {
        Optional<Owner> currentUser = usersService.getCurrentUser(authentication);
        if (currentUser.isPresent()){
            boolean isJoined = meetingsService.joinMeeting(currentUser.get(), meetingId);
            if (isJoined) {
                return ResponseEntity.ok().build();
            } else
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/meetings/{meetingId}/unjoin")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> unjoinMeeting(@RequestHeader(name = "Authorization") String token,
                                           @PathVariable Long meetingId, Authentication authentication) {
        Optional<Owner> currentUser = usersService.getCurrentUser(authentication);
        if (currentUser.isPresent()) {
            boolean isUnjoined = meetingsService.unjoinMeeting(currentUser.get(), meetingId);
            if (isUnjoined) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
