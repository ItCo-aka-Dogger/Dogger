package ru.itis.dogger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.Meeting;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMeetingDto {
    private Long id;
    private String name;
    private String description;
    private Timestamp date;
    private Double coordinateX;
    private Double coordinateY;
    private Long creatorId;
    private String creatorLogin;
    private int participants_count;

    public static SimpleMeetingDto from(Meeting meeting) {
        SimpleMeetingDto meetingDto = new SimpleMeetingDto();
        meetingDto.setId(meeting.getId());
        meetingDto.setName(meeting.getName());
        meetingDto.setDescription(meeting.getDescription());
        meetingDto.setDate(meeting.getDate());
        meetingDto.setCoordinateX(meeting.getCoordinateX());
        meetingDto.setCoordinateY(meeting.getCoordinateY());
        meetingDto.setCreatorId(meeting.getCreator().getId());
        meetingDto.setCreatorLogin(meeting.getCreator().getLogin());
        meetingDto.setParticipants_count(meeting.getParticipants().size());
        return meetingDto;
    }
}
