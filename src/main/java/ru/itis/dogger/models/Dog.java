package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dog {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String breed;

    private Date date_of_birth;

    private Boolean sex;

    private String size;

    private String photo_path;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
