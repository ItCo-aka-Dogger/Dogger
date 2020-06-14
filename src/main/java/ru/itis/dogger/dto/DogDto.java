package ru.itis.dogger.dto;

import lombok.Data;
import ru.itis.dogger.models.Dog;

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
