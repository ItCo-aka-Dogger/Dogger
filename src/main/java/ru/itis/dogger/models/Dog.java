package ru.itis.dogger.models;

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
    @JoinColumn(name = "owner_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Owner owner;

    public Dog(Long id, String name, String breed, Timestamp dateOfBirth, String sex, String size, String information) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.size = size;
        this.information = information;
    }

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
                ", owner='" + owner.getLogin() + '\'' +
                '}';
    }
}
