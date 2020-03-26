package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkingArea {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String photo_path;

    private String description;

    private Double coordinateX;
    private Double coordinateY;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Owner creator;

    //TODO: comments + likes in release 2.0
}
