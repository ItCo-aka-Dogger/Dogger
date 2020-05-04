package ru.itis.dogger.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class NewMeetingDto {
    private String name;
    private String description;
    private Timestamp date;
    private Double coordinateX; //Maybe should be changed to String
    private Double coordinateY; //Depends on further client realization (Timur is responsible)
}
