package ru.itis.dogger.services;

import ru.itis.dogger.dto.DogDto;
import ru.itis.dogger.dto.EditDogDto;
import ru.itis.dogger.models.Owner;

import java.util.List;

public interface DogsService {

    void addDog(DogDto dto, Owner owner);

    List<EditDogDto> editDogs(List<EditDogDto> dtos);
}
