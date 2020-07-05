package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.dogs.DogDto;
import ru.itis.dogger.dto.dogs.EditDogDto;
import ru.itis.dogger.models.owner.Dog;
import ru.itis.dogger.models.owner.Owner;
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

//        if (!dto.getName().equals("")) {
//            dog.setName(dto.getName());
//        }
//        if (!dto.getBreed().equals("")) {
//            dog.setBreed(dto.getBreed());
//        }
//        if (!dto.getInformation().equals("")) {
//            dog.setInformation(dto.getInformation());
//        }
//        if (dto.getDateOfBirth() != null) {
//            dog.setDateOfBirth(dto.getDateOfBirth());
//        }
        dogsRepository.save(dog);
    }
}
