package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dogger.models.place.Review;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
}
