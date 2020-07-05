package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.enums.ContactType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

  private ContactType contactType;

  private String value;

}
