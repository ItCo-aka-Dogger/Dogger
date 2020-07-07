package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.owner.Dog;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface DogsRepository extends JpaRepository<Dog, Long> {

    @Transactional
    @Modifying
    @Query("delete from Dog d where d.id = :dogId")
    void deleteDogById(@Param("dogId") Long dogId);

    Optional<Dog> findDogById(Long dogId);
}
