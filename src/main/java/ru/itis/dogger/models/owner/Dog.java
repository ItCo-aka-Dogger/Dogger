package ru.itis.dogger.models.owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String breed;

    private Timestamp dateOfBirth;

    private String sex;

    private String size;

    private String photo_path;

    private String information;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Owner owner;

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", sex='" + sex + '\'' +
                ", size='" + size + '\'' +
                ", information='" + information + '\'' +
                '}';
    }
}
