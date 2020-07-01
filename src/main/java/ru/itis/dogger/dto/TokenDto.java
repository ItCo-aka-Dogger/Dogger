package ru.itis.dogger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.enums.TokenStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String value;
    private TokenStatus status;
}
