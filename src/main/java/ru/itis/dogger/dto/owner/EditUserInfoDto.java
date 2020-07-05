package ru.itis.dogger.dto.owner;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
public class EditUserInfoDto {
    private String name;
    private String surname;
    private String photo_path;
    private Timestamp dateOfBirth;
    private String city;
    private String district;
    private Map<String, String> contacts;
}