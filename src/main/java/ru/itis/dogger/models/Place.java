package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.itis.dogger.enums.AmenityForDog;
import ru.itis.dogger.enums.PlaceType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {

  @Id
  private String id;
  private String name;
  private String photoPath;
  private String address;
  private Double longitude;
  private Double latitude;

  @Enumerated(EnumType.STRING)
  private PlaceType type;

  @Enumerated(EnumType.STRING)
  private List<AmenityForDog> amenities;

  // Creator document id stored here
  private String creator;

  private List<Comment> comments;

  private Timecard timecard;

  @MapKeyEnumerated(EnumType.STRING)
  private List<Contact> contacts;

  public void addComment(Comment newComment) {
    comments.add(newComment);
  }
}
