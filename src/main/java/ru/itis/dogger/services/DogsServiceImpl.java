package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.DogDto;
import ru.itis.dogger.dto.EditDogDto;
import ru.itis.dogger.models.Dog;
import ru.itis.dogger.models.Owner;
import ru.itis.dogger.repositories.DogsRepository;

import java.util.List;

@Service
public class DogsServiceImpl implements DogsService {

    private DogsRepository dogsRepository;

    @Autowired
    public DogsServiceImpl(DogsRepository dogsRepository) {
        this.dogsRepository = dogsRepository;
    }

    @Override
    public void addDog(DogDto dto, Owner owner) {
        Dog newDog = new Dog();
        newDog.setBreed(dto.getBreed());
        newDog.setName(dto.getName());
        newDog.setDateOfBirth(dto.getDateOfBirth());
        newDog.setSex(dto.getSex());
        newDog.setSize(dto.getSize());
        newDog.setInformation(dto.getInformation());
        newDog.setOwner(owner);
        dogsRepository.save(newDog);
    }

    @Override
    public List<EditDogDto> editDogs(List<EditDogDto> dtos) {
        dtos.forEach(this::editDogInfo);
        return dtos;
    }

    private void editDogInfo(EditDogDto dto) {
        Dog dog = dogsRepository.findById(dto.getId()).get();
        dog.setBreed(dto.getBreed());
        dog.setDateOfBirth(dto.getDateOfBirth());
        dog.setInformation(dto.getInformation());
        dog.setName(dto.getName());
        dogsRepository.save(dog);
    }
}
