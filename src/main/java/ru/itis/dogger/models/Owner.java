package ru.itis.dogger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "dogs")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
    private String fullName;
    private String email;
    private Date dateOfBirth;
    private String activationCode;
    private Boolean active;
    private String photo_path;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    private List<Dog> dogs;

    @ManyToMany(mappedBy = "participants")
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "author")
    @OrderBy("date DESC")
    private List<Question> questions;

    @OneToMany(mappedBy = "author")
    @OrderBy("date DESC")
    private List<Answer> answers;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Meeting> myMeetings;

    public Owner(String login, String password, String fullName, String email) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }
}
