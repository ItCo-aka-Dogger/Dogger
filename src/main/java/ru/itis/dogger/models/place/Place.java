package ru.itis.dogger.models.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.itis.dogger.models.contacts.PlaceContact;
import ru.itis.dogger.models.meeting.Meeting;
import ru.itis.dogger.models.owner.Owner;

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

    private String address;

    private Double longitude;
    private Double latitude;

    private boolean verified;

    @OneToOne
    @JoinColumn(name = "type_id")
    private PlaceType type;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Amenity> amenities;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Owner creator;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Review> reviews;

    @OneToOne
    @JoinColumn(name = "timecard_id")
    private Timecard timecard;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceContact> contacts;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "favoritePlaces")
    private List<Owner> usersWithSuchFavorite;
}
