package ua.rdev.nure.mppzbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "measurements")
@Getter
@NoArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @ManyToOne(cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Column(nullable = false)
    private long timestamp;

    @Setter
    @Column(name = "resting_pulse", nullable = false)
    private int restingPulse;

    @Setter
    @Column(name = "glucose_level", nullable = false)
    private int glucoseLevel;

    @Setter
    @Column(nullable = false)
    private int temperature;

    @Setter
    @Column(name = "sleep_duration", nullable = false)
    private int sleepDuration;

    @Setter
    @Column(name = "steps_count", nullable = false)
    private int stepsCount;

    @Setter
    @Column(nullable = false)
    private int calories;
}
