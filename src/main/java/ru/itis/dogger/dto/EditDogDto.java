package ru.itis.dogger.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EditDogDto {
    private Long id;
    private String name;
    private String breed;
    private Timestamp dateOfBirth;
    private String information;
}
