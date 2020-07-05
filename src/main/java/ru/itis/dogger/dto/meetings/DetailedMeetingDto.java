package ru.itis.dogger.dto.meetings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.dto.OwnerDto;
import ru.itis.dogger.models.Meeting;

import java.util.Date;
import java.util.List;

/* All detailed info about meeting */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedMeetingDto {

    private String id;
    private String name;
    private String description;
    private Date date;
    private Double longitude;
    private Double latitude;
    private String creatorId;
    private List<OwnerDto> participants;

    public static DetailedMeetingDto from(Meeting meeting) {
        DetailedMeetingDto meetingDto = new DetailedMeetingDto();
        meetingDto.setId(meeting.getId());
        meetingDto.setName(meeting.getName());
        meetingDto.setDescription(meeting.getDescription());
        meetingDto.setDate(meeting.getDate());
        meetingDto.setLongitude(meeting.getLongitude());
        meetingDto.setLatitude(meeting.getLatitude());
        meetingDto.setCreatorId(meeting.getCreatorId());

        // TODO: Handle participants extraction on a Service / Repository layer
        if (!meeting.getParticipantsIds().isEmpty()) {
            throw new UnsupportedOperationException("Handle participants extraction on a Service / Repository layer");
        }
        return meetingDto;
    }
}
