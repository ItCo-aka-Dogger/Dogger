package ru.itis.dogger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedMeetingDto {
    private Long id;
    private String name;
    private String description;

    private Timestamp date;

    private Double coordinateX;
    private Double coordinateY;
    private Owner creator;
    private List<Owner> participants;

    public static DetailedMeetingDto from(Meeting meeting) {
        DetailedMeetingDto meetingDto = new DetailedMeetingDto();
        meetingDto.setId(meeting.getId());
        meetingDto.setName(meeting.getName());
        meetingDto.setDescription(meeting.getDescription());
        meetingDto.setDate(meeting.getDate());
        meetingDto.setCoordinateX(meeting.getCoordinateX());
        meetingDto.setCoordinateY(meeting.getCoordinateY());
        meetingDto.setCreator(meeting.getCreator());
        meetingDto.setParticipants(meeting.getParticipants());

        return meetingDto;
    }
}
