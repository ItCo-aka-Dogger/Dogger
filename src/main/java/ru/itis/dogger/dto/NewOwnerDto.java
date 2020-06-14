package ru.itis.dogger.dto;

import lombok.Data;

@Data
public class NewOwnerDto {
    private Long id;
    private String login;
    private String password;
    private String fullName;
    private String email;
    private String city;
}
