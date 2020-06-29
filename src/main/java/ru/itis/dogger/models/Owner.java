package ru.itis.dogger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.itis.dogger.enums.Contact;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private String fullName;
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
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Meeting> myMeetings;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Place> createdPlaces;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_contact")
    @MapKeyColumn(name = "contact_type")
    @MapKeyClass(Contact.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "contact_value")
    private Map<Contact, String> contacts;

    public Owner(String password, String fullName, String email, String city) {
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.city = city;
    }

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
