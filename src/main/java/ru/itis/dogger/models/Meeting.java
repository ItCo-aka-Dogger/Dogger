package ru.itis.dogger.models;

import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Owner creator;

    @ManyToMany
    @JoinTable(
            name = "meeting_owner",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id"))
    private List<Owner> participants;
}
