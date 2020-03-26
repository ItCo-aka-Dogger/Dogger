package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meeting {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Date date;

    private Double coordinateX;
    private Double coordinateY;

    @ManyToMany(mappedBy = "meetings")
    private List<Owner> participants;
}
