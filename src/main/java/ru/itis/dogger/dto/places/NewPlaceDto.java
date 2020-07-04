package ru.itis.dogger.dto.places;

import lombok.Data;
import ru.itis.dogger.models.Timecard;

import java.util.List;
import java.util.Map;

@Data
public class NewPlaceDto {
    private String name;
    private String photoPath;
    private String address;
    private Double longitude;
    private Double latitude;
    private String placeType;
    private List<String> amenities;
    private Map<String, String> contacts;
    private Timecard timecard;
}
