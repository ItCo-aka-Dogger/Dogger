package ru.itis.dogger.dto.places;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.place.Place;
import ru.itis.dogger.models.place.PlaceType;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePlaceDto {

    private String id;
    private String name;
    private Double longitude;
    private Double latitude;
    private PlaceType type;

    public static SimplePlaceDto from(Place place) {
        SimplePlaceDto simplePlaceDto = new SimplePlaceDto();
        simplePlaceDto.setId(place.getId());
        simplePlaceDto.setName(place.getName());
        simplePlaceDto.setLongitude(place.getLongitude());
        simplePlaceDto.setLatitude(place.getLatitude());
        simplePlaceDto.setType(place.getType());
        return simplePlaceDto;
    }
}
