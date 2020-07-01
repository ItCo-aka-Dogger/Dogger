package ru.itis.dogger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.*;

import java.util.Date;
import java.util.List;

/*excluded personal info like activeCode, password hash and etc*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    private Long id;
    private String email;
    private String fullName;
    private Date dateOfBirth;
    private String photo_path;
    private List<Dog> dogs;

    public static OwnerDto from(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setFullName(owner.getFullName());
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setDateOfBirth(owner.getDateOfBirth());
        ownerDto.setPhoto_path(owner.getPhoto_path());
        ownerDto.setDogs(owner.getDogs());
        return ownerDto;
    }
}
