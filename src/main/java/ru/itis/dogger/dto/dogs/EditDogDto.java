package ru.itis.dogger.dto.dogs;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EditDogDto {
    private String id;
    private String name;
    private String breed;
    private Timestamp dateOfBirth;
    private String information;
}
