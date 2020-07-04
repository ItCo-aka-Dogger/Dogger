package ru.itis.dogger.dto.places;

import lombok.Data;
import ru.itis.dogger.dto.NewContactDto;
import ru.itis.dogger.models.place.Timecard;

import java.util.List;

@Data
public class NewPlaceDto {
    private String name;
    private String photoPath;
    private String address;
    private Double longitude;
    private Double latitude;
    private Long typeId;
    private List<Long> amenitiesIds;
    private List<NewContactDto> contacts;
    private Timecard timecard;
}
