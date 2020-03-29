package ru.itis.dogger.services;

import ru.itis.dogger.dto.OwnerDto;
import ru.itis.dogger.dto.TokenDto;

public interface UsersService {
    void signUp(OwnerDto dto);
    TokenDto login(OwnerDto dto);
}
