package ru.itis.dogger.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.dto.places.DetailedPlaceDto;
import ru.itis.dogger.models.contacts.OwnerContact;
import ru.itis.dogger.models.owner.Dog;
import ru.itis.dogger.models.owner.Owner;
import ru.itis.dogger.models.place.Place;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*excluded personal info like activeCode, password hash and etc*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String city;
    private String district;
    private String photo_path;
    private List<Dog> dogs;
    private List<OwnerContact> contacts;
    private List<DetailedPlaceDto> favoritePlaces;

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
        ownerDto.setFavoritePlaces(owner.getFavoritePlaces().stream()
                .map(DetailedPlaceDto::from).collect(Collectors.toList()));
        return ownerDto;
    }
}
