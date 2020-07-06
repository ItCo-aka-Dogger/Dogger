package ru.itis.dogger.dto.dogs;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NewDogDto {
    private String breed;
    private String information;
    private String name;
    private Timestamp dateOfBirth;
    private String sex;
    private String size;
    private String photo_path;
}
