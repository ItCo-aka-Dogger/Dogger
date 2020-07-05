package ru.itis.dogger.dto.dogs;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DogDto {
    private String breed;
    private String information;
    private String name;
    private Timestamp dateOfBirth;
    private String sex;
    private String size;
}
