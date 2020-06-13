package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.enums.AmenityForDog;
import ru.itis.dogger.enums.PlaceType;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String photo_path;

    private String description;

    private Double coordinateX;
    private Double coordinateY;

    @Enumerated(EnumType.STRING)
    private PlaceType type;

    @ElementCollection(targetClass = AmenityForDog.class)
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name = "place_amenity")
    @Column(name = "amenity") // Column name in place_amenity
    private List<AmenityForDog> amenities;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Owner creator;

    //TODO: comments + rating in release 2.0
}
