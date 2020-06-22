package ru.itis.dogger.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.enums.TokenStatus;

@Data
@NoArgsConstructor
public class TokenDto {
    private String value;
    private TokenStatus status;
}
