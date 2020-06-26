package ru.itis.dogger.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
public class EditUserInfoDto {
    private String name;
    private String fullName;
    private Timestamp dateOfBirth;
    private String city;
    private String phoneNumber;
    private String district;
    private Map<String, String> contacts;
}