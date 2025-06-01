package ua.rdev.nure.mppzbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.rdev.nure.mppzbackend.entities.Measurement;
import ua.rdev.nure.mppzbackend.entities.User;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findAllByUserAndTimestampAfter(User user, long after);

    Optional<Measurement> findByUserOrderByTimestampDesc(User user);
}
