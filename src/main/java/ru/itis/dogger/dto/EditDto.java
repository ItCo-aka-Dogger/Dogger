package ru.itis.dogger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class EditDto {
    private String email;
    private String password;
    private String fullName;
    private Timestamp dateOfBirth;
}