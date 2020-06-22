package ru.itis.dogger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Timestamp date;

    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Owner author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

}
