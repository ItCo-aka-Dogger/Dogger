package ru.itis.dogger.models.meeting;

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
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Timestamp date;

    private Double longitude;
    private Double latitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Owner creator;

    @JsonIgnore
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "meeting_owner",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id"))
    private List<Owner> participants;
}
