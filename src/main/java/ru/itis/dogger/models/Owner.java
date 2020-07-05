package ru.itis.dogger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "dogs")
@Builder
public class Owner {

    @Id
    private String id;
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

    private List<Dog> dogs;

    @JsonIgnore
    private List<Meeting> meetings;

    @JsonIgnore
    private List<Meeting> myMeetings;

    @JsonIgnore
    private List<String> createdPlaces;

    @JsonIgnore
    private List<Comment> comments;

    @MapKeyEnumerated(EnumType.STRING)
    private List<Contact> contacts;

    public Owner(String password, String surname, String email, String city) {
        this.password = password;
        this.surname = surname;
        this.email = email;
        this.city = city;
    }

    //TODO: forum release 2.0

    @JsonIgnore
    private List<Question> questions;

    @JsonIgnore
    private List<Answer> answers;
}
