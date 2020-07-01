package ru.itis.dogger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.Owner;

import java.util.List;

/* user info that will be used in lists of something (meetings, comments, etc)*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleOwnerDto {
    private Long id;
    private String fullName;
    private String photo_path;

    public static SimpleOwnerDto from(Owner owner) {
        SimpleOwnerDto ownerDto = new SimpleOwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setFullName(owner.getFullName());
        ownerDto.setPhoto_path(owner.getPhoto_path());
        return ownerDto;
    }
}
