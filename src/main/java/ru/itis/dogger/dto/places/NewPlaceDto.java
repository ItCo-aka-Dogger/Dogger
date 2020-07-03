package ru.itis.dogger.dto.places;

import lombok.Data;
import ru.itis.dogger.models.place.PlaceType;
import ru.itis.dogger.models.place.Timecard;

import java.util.List;
import java.util.Map;

@Data
public class NewPlaceDto {
    private String name;
    private String photoPath;
    private String address;
    private Double longitude;
    private Double latitude;
    private String typeId;
    private List<String> amenitiesIds;
    private Map<String, String> contacts;
    private Timecard timecard;
}
