package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String description;

    private Double coordinateX;
    private Double coordinateY;

    private String type;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Owner creator;

    //TODO: comments + likes in release 2.0
}
