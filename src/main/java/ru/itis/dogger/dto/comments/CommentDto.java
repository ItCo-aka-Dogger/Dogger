package ru.itis.dogger.dto.comments;

import lombok.Data;
import ru.itis.dogger.dto.owner.SimpleOwnerDto;
import ru.itis.dogger.models.place.Comment;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CommentDto {

    private Long id;
    private String text;
    private Timestamp date;
    private Integer score;
    private SimpleOwnerDto author;
    private List<String> attachments;

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setDate(comment.getDate());
        commentDto.setScore(comment.getScore());
        commentDto.setAuthor(SimpleOwnerDto.from(comment.getAuthor()));
        commentDto.setAttachments(comment.getAttachments());
        return commentDto;
    }

}
