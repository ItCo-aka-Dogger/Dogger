package ru.itis.dogger.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.owner.Owner;

/* user info that will be used in lists of something (meetings, reviews, etc)*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleOwnerDto {
    private String id;
    private String name;
    private String surname;
    private String photo_path;

    public static SimpleOwnerDto from(Owner owner) {
        SimpleOwnerDto ownerDto = new SimpleOwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setName(owner.getName());
        ownerDto.setSurname(owner.getSurname());
        ownerDto.setPhoto_path(owner.getPhoto_path());
        return ownerDto;
    }
}
