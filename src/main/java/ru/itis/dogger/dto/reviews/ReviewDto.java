package ru.itis.dogger.dto.reviews;

import lombok.Data;
import ru.itis.dogger.dto.owner.SimpleOwnerDto;
import ru.itis.dogger.models.place.Review;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ReviewDto {

    private Long id;
    private String comment;
    private Timestamp date;
    private Integer score;
    private SimpleOwnerDto author;
    private List<String> attachments;

    public static ReviewDto from(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setComment(review.getComment());
        reviewDto.setDate(review.getDate());
        reviewDto.setScore(review.getScore());
        reviewDto.setAuthor(SimpleOwnerDto.from(review.getAuthor()));
        reviewDto.setAttachments(review.getAttachments());
        return reviewDto;
    }

}
