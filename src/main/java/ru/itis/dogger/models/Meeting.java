package ru.itis.dogger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Timestamp date;

    private Double coordinateX;
    private Double coordinateY;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Owner creator;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "meeting_owner",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id"))
    private List<Owner> participants;
}
