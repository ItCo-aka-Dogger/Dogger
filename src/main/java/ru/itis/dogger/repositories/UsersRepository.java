package ru.itis.dogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.dogger.models.Owner;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByLogin(String login);

    Optional<Owner> findByActivationCode(String code);
}
