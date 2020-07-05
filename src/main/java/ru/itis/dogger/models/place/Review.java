package ru.itis.dogger.models.place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private String id;

    private String comment;

    private Timestamp date;

    private Integer score;

    private List<String> attachments;
}
