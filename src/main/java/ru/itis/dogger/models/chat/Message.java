package ru.itis.dogger.models.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dogger.models.meeting.Meeting;
import ru.itis.dogger.models.owner.Owner;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
