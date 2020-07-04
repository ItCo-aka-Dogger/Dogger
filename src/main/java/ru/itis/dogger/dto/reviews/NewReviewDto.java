package ru.itis.dogger.dto.reviews;

import lombok.Data;

import java.util.List;

@Data
public class NewReviewDto {

    private String comment;
    private Integer score;
    private List<String> attachments;
}
