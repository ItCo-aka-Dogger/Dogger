package ru.itis.dogger.dto;

import lombok.Data;

@Data
public class OwnerDto {
    private Long id;
    private String login;
    private String password;
    private String fullName;
}
