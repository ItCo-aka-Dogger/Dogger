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
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllMeetings() {
        List<SimpleMeetingDto> meetingDtos = meetingsService.getAllMeetings().stream().map(SimpleMeetingDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(meetingDtos);
    }

    @GetMapping("/meetings/joined")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMeetingsInWhichUserParticipates(Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        List<SimpleMeetingDto> meetingDtos = meetingsService.getParticipatedMeetings(currentUser.getId())
                .stream().map(SimpleMeetingDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(meetingDtos);
    }

    //TODO: переспросить у Тимура
    //вообще созданные юзером митинги хранятся в самом юзере в отношении OneToMany
    //поэтому нет смысла в этом методе, если Тимур будет хранить митинги оттуда
    //но если кто-то создаст еще один митинг, Тимуру надо будет послать GET /profile заново что myMeetings обновились - удобно ли ему?
    @GetMapping("/meetings/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMeetingsCreatedByMe(Authentication authentication) {
        Owner currentUser = ((UserDetailsImpl) authentication.getDetails()).getUser();
        List<SimpleMeetingDto> meetingDtos = currentUser.getMyMeetings()
                .stream().map(SimpleMeetingDto::from).collect(Collectors.toList());
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

    @PostMapping("/meetings/{meetingId}/join")
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
