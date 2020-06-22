package ru.itis.dogger.dto;

import lombok.Data;
import ru.itis.dogger.validation.Email;

@Data
public class NewOwnerDto {
    private Long id;
    private String password;
    private String fullName;

    @Email
    private String email;
    private String city;
}
