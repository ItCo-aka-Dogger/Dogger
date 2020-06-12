package ru.itis.dogger.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EditDto {
    private String fullName;
    private Timestamp dateOfBirth;
    private String city;
    private String phoneNumber;
}