package ru.itis.dogger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @Column(name = "question_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Owner author;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

}
