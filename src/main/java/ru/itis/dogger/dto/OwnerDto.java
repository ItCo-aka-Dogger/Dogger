package ru.itis.dogger.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.itis.dogger.models.*;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.Date;
import java.util.List;

/*excluded personal info like activeCode, password hash and etc*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    private Long id;
    private String login;
    private String fullName;
    private String email;
    private Date dateOfBirth;
    private String photo_path;
    private List<Dog> dogs;
    private List<Meeting> meetings;

    public static OwnerDto from(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();

        ownerDto.setId(owner.getId());
        ownerDto.setLogin(owner.getLogin());
        ownerDto.setFullName(owner.getFullName());
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setDateOfBirth(owner.getDateOfBirth());
        ownerDto.setPhoto_path(owner.getPhoto_path());
        ownerDto.setDogs(owner.getDogs());
        ownerDto.setMeetings(owner.getMeetings());

        return ownerDto;
    }
}
