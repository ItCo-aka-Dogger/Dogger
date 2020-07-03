package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dogger.models.place.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
