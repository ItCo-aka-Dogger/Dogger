package ru.itis.dogger.models.forum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.owner.Owner;

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
