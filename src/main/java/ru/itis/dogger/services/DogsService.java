package ru.itis.dogger.services;

import ru.itis.dogger.dto.dogs.NewDogDto;
import ru.itis.dogger.models.owner.Dog;
import ru.itis.dogger.models.owner.Owner;

import java.util.Optional;

public interface DogsService {
    void addDog(NewDogDto dto, Owner owner);

    Dog editDog(NewDogDto dto, Long dogId);

    Optional<Dog> getDogById(Long id);

    void deleteDog(Long dogId);
}
