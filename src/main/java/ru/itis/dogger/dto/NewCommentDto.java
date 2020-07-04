package ru.itis.dogger.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewCommentDto {

    private String text;
    private Integer score;
    private List<String> attachments;
}
