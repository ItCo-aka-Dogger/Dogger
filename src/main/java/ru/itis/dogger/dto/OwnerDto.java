package ru.itis.dogger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.Contact;
import ru.itis.dogger.models.Dog;
import ru.itis.dogger.models.Owner;

import java.util.Date;
import java.util.List;

/*excluded personal info like activeCode, password hash and etc*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    private String id;
    private String email;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String city;
    private String district;
    private String photo_path;
    private List<Dog> dogs;
    private List<Contact> contacts;

    public static OwnerDto from(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setName(owner.getName());
        ownerDto.setSurname(owner.getSurname());
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setDateOfBirth(owner.getDateOfBirth());
        ownerDto.setCity(owner.getCity());
        ownerDto.setDistrict(owner.getDistrict());
        ownerDto.setPhoto_path(owner.getPhoto_path());
        ownerDto.setDogs(owner.getDogs());
        ownerDto.setContacts(owner.getContacts());
        return ownerDto;
    }
}
