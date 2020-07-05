package ru.itis.dogger.dto.places;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.dto.reviews.ReviewDto;
import ru.itis.dogger.models.place.*;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedPlaceDto {

    private String id;
    private String name;
    private String photoPath;
    private Double longitude;
    private Double latitude;
    private String address;
    private PlaceType type;
    private List<AmenityForDog> amenities;
    private Timecard timecard;
    private List<PlaceContact> contacts;
    private Double rating;
    private Integer reviewsCount;
    private List<ReviewDto> reviews;

    public static DetailedPlaceDto from(Place place) {
        DetailedPlaceDto detailedPlaceDto = new DetailedPlaceDto();
        detailedPlaceDto.setId(place.getId());
        detailedPlaceDto.setName(place.getName());
        detailedPlaceDto.setPhotoPath(place.getPhotoPath());
        detailedPlaceDto.setLongitude(place.getLongitude());
        detailedPlaceDto.setLatitude(place.getLatitude());
        detailedPlaceDto.setAddress(place.getAddress());
        detailedPlaceDto.setType(place.getType());
        detailedPlaceDto.setAmenities(place.getAmenities());
        detailedPlaceDto.setTimecard(place.getTimecard());
        detailedPlaceDto.setContacts(place.getContacts());

        if (place.getReviews() != null && place.getReviews().size() > 0) {
            Double sum = 0.0;
            for (Review c : place.getReviews()) {
                sum += c.getScore();
            }
            detailedPlaceDto.setRating(sum / place.getReviews().size());
            detailedPlaceDto.setReviewsCount(place.getReviews().size());
            detailedPlaceDto.setReviews(place.getReviews().stream().map(ReviewDto::from).collect(Collectors.toList()));
        } else {
            detailedPlaceDto.setReviewsCount(0);
        }
        return detailedPlaceDto;
    }

}
