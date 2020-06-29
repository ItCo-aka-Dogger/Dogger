package ru.itis.dogger.dto;

import lombok.Data;
import ru.itis.dogger.models.Timecard;

import java.util.List;
import java.util.Map;

@Data
public class NewPlaceDto {
    private String name;
    private String photoPath;
    private String description;
    private Double coordinateX;
    private Double coordinateY;
    private String placeType;
    private List<String> amenities;
    private Map<String, String> contacts;
    private Timecard timecard;
}
