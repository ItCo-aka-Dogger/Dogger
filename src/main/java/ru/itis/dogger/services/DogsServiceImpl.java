package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.dogs.NewDogDto;
import ru.itis.dogger.models.owner.Dog;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.repositories.DogsRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class DogsServiceImpl implements DogsService {

    private DogsRepository dogsRepository;

    @Autowired
    public DogsServiceImpl(DogsRepository dogsRepository) {
        this.dogsRepository = dogsRepository;
    }

    @Autowired
    private EntityManager entityManager;

    @Override
    public void addDog(NewDogDto dto, Owner owner) {
        Dog newDog = new Dog();
        newDog.setBreed(dto.getBreed());
        newDog.setName(dto.getName());
        newDog.setDateOfBirth(dto.getDateOfBirth());
        newDog.setSex(dto.getSex());
        newDog.setSize(dto.getSize());
        newDog.setInformation(dto.getInformation());
        newDog.setPhoto_path(dto.getPhoto_path());
        newDog.setOwner(owner);
        dogsRepository.save(newDog);
    }

    @Override
    public Dog editDog(NewDogDto dto, Long dogId) {
        Dog dog = dogsRepository.getOne(dogId);
        dog.setBreed(dto.getBreed());
        dog.setDateOfBirth(dto.getDateOfBirth());
        dog.setInformation(dto.getInformation());
        dog.setName(dto.getName());
        dog.setPhoto_path(dto.getPhoto_path());
        dog.setSex(dto.getSex());
        dog.setSize(dto.getSize());
        return dogsRepository.save(dog);
    }

    @Override
    public Optional<Dog> getDogById(Long id) {
        return dogsRepository.findDogById(id);
    }

    @Override
    public void deleteDog(Long dogId) {
        dogsRepository.deleteDogById(dogId);
    }
}
