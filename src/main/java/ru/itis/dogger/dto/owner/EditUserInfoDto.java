package ru.itis.dogger.dto.owner;

import lombok.Data;
import ru.itis.dogger.dto.NewContactDto;

import java.sql.Timestamp;
import java.util.List;

@Data
public class EditUserInfoDto {
    private String name;
    private String surname;
    private String photo_path;
    private Timestamp dateOfBirth;
    private String city;
    private String district;
    private List<NewContactDto> contacts;
}