package ru.otus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String name);
}
