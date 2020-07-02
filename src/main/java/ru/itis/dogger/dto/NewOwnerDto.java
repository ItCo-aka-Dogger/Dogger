package ru.itis.dogger.dto;

import lombok.Data;
import ru.itis.dogger.validation.Email;

@Data
public class NewOwnerDto {
    private Long id;
    private String password;
    private String name;
    private String surname;

    @Email
    private String email;
}
