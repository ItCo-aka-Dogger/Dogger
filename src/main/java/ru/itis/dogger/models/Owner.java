package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;

    private Date dateOfBirth;

    private String photo_path;

    @OneToMany(mappedBy = "owner")
    private List<Dog> dogs;

    @ManyToMany
    @JoinTable(
            name = "meeting_owner",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id"))
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "author")
    @OrderBy("date DESC")
    private List<Question> questions;

    @OneToMany(mappedBy = "author")
    @OrderBy("date DESC")
    private List<Answer> answers;

    public Owner(String login, String password, Date dateOfBirth) {
        this.login = login;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
}
