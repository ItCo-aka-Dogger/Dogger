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
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "message_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Owner sender;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
