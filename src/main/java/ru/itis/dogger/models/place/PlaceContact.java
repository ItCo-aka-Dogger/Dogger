package ru.itis.dogger.models.place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.enums.ContactType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceContact {

    private ContactType contactType;

    private String value;

}
