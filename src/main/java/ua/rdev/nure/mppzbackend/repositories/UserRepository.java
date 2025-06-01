package ua.rdev.nure.mppzbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.rdev.nure.mppzbackend.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
