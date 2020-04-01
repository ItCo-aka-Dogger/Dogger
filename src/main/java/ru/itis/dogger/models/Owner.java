package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
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

    private Date dateOfBirth;

    private String photo_path;

    @OneToMany(mappedBy = "owner")
    @LazyCollection(LazyCollectionOption.TRUE)
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

    public Owner(String login, String password, String fullName) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
    }

//    public List<Dog> getDogs() {
//        List<Dog> dogList = new ArrayList<>();
//        for (Dog dog : dogs) {
//            Dog dog1 = new Dog(dog.getId(), dog.getName(), dog.getBreed(), dog.getDateOfBirth(), dog.getSex(), dog.getSize(), dog.getInformation());
//            dogList.add(dog1);
//        }
//        return dogList;
//    }
}
