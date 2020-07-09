package ru.itis.dogger.models.owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.itis.dogger.models.contacts.OwnerContact;
import ru.itis.dogger.models.forum.Answer;
import ru.itis.dogger.models.forum.Question;
import ru.itis.dogger.models.meeting.Meeting;
import ru.itis.dogger.models.place.Review;
import ru.itis.dogger.models.place.Place;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "dogs")
@Builder
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String activationCode;
    @JsonIgnore
    private Boolean active;

    private String name;
    private String surname;
    private Date dateOfBirth;
    private String photo_path;
    private String city;
    private String district;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Dog> dogs;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "participants")
    private List<Meeting> meetings;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Meeting> myMeetings;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Place> createdPlaces;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<OwnerContact> contacts;

    //TODO: forum release 2.0

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("date DESC")
    private List<Question> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("date DESC")
    private List<Answer> answers;
}
