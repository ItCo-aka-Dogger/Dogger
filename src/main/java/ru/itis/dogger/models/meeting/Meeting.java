package ru.itis.dogger.models.meeting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

    @Id
    private String id;

    private String name;

    private String description;

    private Date date;

    private Double longitude;
    private Double latitude;

    @JsonIgnore
    private String creatorId;

    @JsonIgnore
    private List<String> participantsIds;
}
