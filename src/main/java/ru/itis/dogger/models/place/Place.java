package ru.itis.dogger.models.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.enums.AmenityForDog;
import ru.itis.dogger.enums.Contact;
import ru.itis.dogger.models.owner.Owner;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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

    @OneToOne
    @JoinColumn(name = "type_id")
    private PlaceType type;

    @ElementCollection(targetClass = AmenityForDog.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "place_amenity")
    @Column(name = "amenity")
    private List<AmenityForDog> amenities;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Owner creator;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "timecard_id")
    private Timecard timecard;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "place_contact")
    @MapKeyColumn(name = "contact_type")
    @MapKeyClass(Contact.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "contact_value")
    private Map<Contact, String> contacts;
}
