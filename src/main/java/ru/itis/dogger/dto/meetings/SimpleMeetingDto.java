package ru.itis.dogger.dto.meetings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.dto.SimpleOwnerDto;
import ru.itis.dogger.models.Meeting;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/* Shorted info about meeting for list*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMeetingDto {

    private Long id;
    private String name;
    private Timestamp date;
    private Double longitude;
    private Double latitude;
    private List<SimpleOwnerDto> participants;

    public static SimpleMeetingDto from(Meeting meeting) {
        SimpleMeetingDto meetingDto = new SimpleMeetingDto();
        meetingDto.setId(meeting.getId());
        meetingDto.setName(meeting.getName());
        meetingDto.setDate(meeting.getDate());
        meetingDto.setLongitude(meeting.getLongitude());
        meetingDto.setLatitude(meeting.getLatitude());

        List<SimpleOwnerDto> dtos = meeting.getParticipants().stream()
                .map(SimpleOwnerDto::from).collect(Collectors.toList());
        meetingDto.setParticipants(dtos);

        return meetingDto;
    }
}
