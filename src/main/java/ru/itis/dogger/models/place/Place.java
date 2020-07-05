package ru.itis.dogger.models.place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {

    @Id
    private String id;
    private String name;
    private String photoPath;
    private String address;
    private Double longitude;
    private Double latitude;

    @Enumerated(EnumType.STRING)
    private PlaceType type;

    @Enumerated(EnumType.STRING)
    private List<AmenityForDog> amenities;

    // Creator document id stored here
    private String creatorId;

    private List<Review> reviews;

    private Timecard timecard;

    @MapKeyEnumerated(EnumType.STRING)
    private List<PlaceContact> contacts;

    public void addReview(Review newReview) {
        reviews.add(newReview);
    }
}
