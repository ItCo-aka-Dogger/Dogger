package ru.itis.dogger.dto.places;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.dto.comments.CommentDto;
import ru.itis.dogger.models.place.*;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedPlaceDto {

    private Long id;
    private String name;
    private String photo_path;
    private Double longitude;
    private Double latitude;
    private String address;
    private PlaceType type;
    private List<Amenity> amenities;
    private Timecard timecard;
    private List<PlaceContact> contacts;
    private Double rating;
    private Integer comments_count;
    private List<CommentDto> comments;

    public static DetailedPlaceDto from(Place place) {
        DetailedPlaceDto detailedPlaceDto = new DetailedPlaceDto();
        detailedPlaceDto.setId(place.getId());
        detailedPlaceDto.setName(place.getName());
        detailedPlaceDto.setPhoto_path(place.getPhoto_path());
        detailedPlaceDto.setLongitude(place.getLongitude());
        detailedPlaceDto.setLatitude(place.getLatitude());
        detailedPlaceDto.setAddress(place.getAddress());
        detailedPlaceDto.setType(place.getType());
        detailedPlaceDto.setAmenities(place.getAmenities());
        detailedPlaceDto.setTimecard(place.getTimecard());
        detailedPlaceDto.setContacts(place.getContacts());

        Double sum = 0.0;
        for (Comment c : place.getComments()) {
            sum += c.getScore();
        }
        detailedPlaceDto.setRating(sum / place.getComments().size());
        detailedPlaceDto.setComments_count(place.getComments().size());
        detailedPlaceDto.setComments(place.getComments().stream().map(CommentDto::from).collect(Collectors.toList()));

        return detailedPlaceDto;
    }

}
