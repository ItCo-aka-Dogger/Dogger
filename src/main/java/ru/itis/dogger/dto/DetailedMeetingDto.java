package ru.itis.dogger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.dogger.models.Meeting;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.services.UsersService;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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
    private OwnerDto creator;
    private List<OwnerDto> participants;

    public static DetailedMeetingDto from(Meeting meeting) {
        DetailedMeetingDto meetingDto = new DetailedMeetingDto();
        meetingDto.setId(meeting.getId());
        meetingDto.setName(meeting.getName());
        meetingDto.setDescription(meeting.getDescription());
        meetingDto.setDate(meeting.getDate());
        meetingDto.setCoordinateX(meeting.getCoordinateX());
        meetingDto.setCoordinateY(meeting.getCoordinateY());
        meetingDto.setCreator(OwnerDto.from(meeting.getCreator()));

        List<OwnerDto> dtos = meeting.getParticipants().stream()
                .map(OwnerDto::from).collect(Collectors.toList());
        meetingDto.setParticipants(dtos);

        return meetingDto;
    }
}
