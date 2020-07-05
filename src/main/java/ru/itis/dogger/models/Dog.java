package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dog {
    private String id;

    private String name;

    private String breed;

    private Timestamp dateOfBirth;

    private String sex;

    private String size;

    private String photo_path;

    private String information;

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
