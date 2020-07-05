package ru.itis.dogger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dogger.dto.dogs.DogDto;
import ru.itis.dogger.dto.dogs.EditDogDto;
import ru.itis.dogger.models.owner.Dog;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DogsServiceImpl implements DogsService {

    private UsersRepository usersRepository;

    @Autowired
    public DogsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
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

        owner.addDog(newDog);
        usersRepository.save(owner);
    }

    @Override
    public List<EditDogDto> editDogs(Owner currentUser, List<EditDogDto> dtos) {
        dtos.forEach(editDogDto -> editDogInfo(currentUser, editDogDto));
        return dtos;
    }

    private void editDogInfo(Owner currentUser, EditDogDto dto) {
        Optional<Dog> dog = currentUser.getDogs().stream()
                .filter(oneOdDogs -> oneOdDogs.getId().equals(dto.getId()))
                .findFirst();
        dog.ifPresent(dog12 -> {
            dog12.setBreed(dto.getBreed());
            dog12.setDateOfBirth(dto.getDateOfBirth());
            dog12.setInformation(dto.getInformation());
            dog12.setName(dto.getName());
//        if (!dto.getName().equals("")) {
////            dog.setName(dto.getName());
////        }
////        if (!dto.getBreed().equals("")) {
////            dog.setBreed(dto.getBreed());
////        }
////        if (!dto.getInformation().equals("")) {
////            dog.setInformation(dto.getInformation());
////        }
////        if (dto.getDateOfBirth() != null) {
////            dog.setDateOfBirth(dto.getDateOfBirth());
////        }
            // TODO: bad from perdormance endpoint, should use batches instead
            usersRepository.save(currentUser);
        });
    }
}
