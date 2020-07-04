package ru.itis.dogger.models.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.owner.Owner;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private Timestamp date;

    private Integer score;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Owner author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ElementCollection
    private List<String> attachments;

}
