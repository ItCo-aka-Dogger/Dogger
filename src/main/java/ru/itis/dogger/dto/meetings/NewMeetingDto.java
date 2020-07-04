package ru.itis.dogger.dto.meetings;

import lombok.Data;
import java.sql.Timestamp;

/* Form for creating new meeting */

@Data
public class NewMeetingDto {
    private String name;
    private String description;
    private Timestamp date;
    private Double longitude;
    private Double latitude;
}
