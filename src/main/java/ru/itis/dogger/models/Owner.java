package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Owner {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Date date_of_birth;

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
}
