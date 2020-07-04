package ru.itis.dogger.dto.meetings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.dto.OwnerDto;
import ru.itis.dogger.dto.SimpleOwnerDto;
import ru.itis.dogger.models.Meeting;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/* All detailed info about meeting */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedMeetingDto {

    private Long id;
    private String name;
    private String description;
    private Timestamp date;
    private Double longitude;
    private Double latitude;
    private Long creatorId;
    private List<OwnerDto> participants;

    public static DetailedMeetingDto from(Meeting meeting) {
        DetailedMeetingDto meetingDto = new DetailedMeetingDto();
        meetingDto.setId(meeting.getId());
        meetingDto.setName(meeting.getName());
        meetingDto.setDescription(meeting.getDescription());
        meetingDto.setDate(meeting.getDate());
        meetingDto.setLongitude(meeting.getLongitude());
        meetingDto.setLatitude(meeting.getLatitude());
        meetingDto.setCreatorId(meeting.getCreator().getId());

        List<OwnerDto> dtos = meeting.getParticipants().stream()
                .map(OwnerDto::from).collect(Collectors.toList());
        meetingDto.setParticipants(dtos);

        return meetingDto;
    }
}
