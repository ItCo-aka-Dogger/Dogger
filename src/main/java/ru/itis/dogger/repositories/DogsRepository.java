package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.owner.Dog;

@Repository
public interface DogsRepository extends JpaRepository<Dog, Long> {
}
