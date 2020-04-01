package ru.itis.dogger.services;

import ru.itis.dogger.dto.DogDto;
import ru.itis.dogger.models.Owner;

public interface DogsService {
    void addDog(DogDto dto, Owner owner);
}
