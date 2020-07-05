package ru.itis.dogger.dto.meetings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.dto.owner.SimpleOwnerDto;
import ru.itis.dogger.models.meeting.Meeting;

import java.util.Date;
import java.util.List;

/* Shorted info about meeting for list*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMeetingDto {

    private String id;
    private String name;
    private Date date;
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

        // TODO: Handle participants extraction on a Service / Repository layer
        if (!meeting.getParticipantsIds().isEmpty()) {
            throw new UnsupportedOperationException("Handle participants extraction on a Service / Repository layer");
        }

        return meetingDto;
    }
}
