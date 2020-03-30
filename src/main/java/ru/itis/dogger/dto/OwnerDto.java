package ru.itis.dogger.dto;

import lombok.Data;

@Data
public class OwnerDto {
    private String login;
    private String password;
    private String fullName;
}
