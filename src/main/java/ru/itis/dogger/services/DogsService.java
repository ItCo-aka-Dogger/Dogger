package ru.itis.dogger.services;

import ru.itis.dogger.dto.dogs.DogDto;
import ru.itis.dogger.dto.dogs.EditDogDto;
import ru.itis.dogger.models.owner.Owner;

import java.util.List;

public interface DogsService {

    void addDog(DogDto dto, Owner owner);

    List<EditDogDto> editDogs(Owner currentUser, List<EditDogDto> dtos);
}
