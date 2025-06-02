package ua.rdev.nure.mppzbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.rdev.nure.mppzbackend.entities.User;
import ua.rdev.nure.mppzbackend.exceptions.EmailTakenException;
import ua.rdev.nure.mppzbackend.repositories.UserRepository;
import ua.rdev.nure.mppzbackend.requests.RegisterRequest;
import ua.rdev.nure.mppzbackend.responses.UserStats;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> get(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User register(RegisterRequest reg) {
        if(userRepository.findByEmail(reg.getEmail()).isPresent()) {
            throw new EmailTakenException(reg.getEmail());
        }

        User user = new User();
        user.setEmail(reg.getEmail());
        user.setPasswordHash(passwordEncoder.encode(reg.getPassword()));
        user.setName(reg.getName());
        user.setNickname(reg.getNickname());
        user.setGender(reg.getGender());
        user.setDateOfBirth(reg.getDateOfBirth());
        user.setHeight(reg.getHeight());
        user.setWeight(reg.getWeight());

        return userRepository.save(user);
    }

    public UserStats getUserStats(User user) {
        double userHeightM = (double)user.getHeight() / 100;
        double imt = (double)user.getWeight() / (userHeightM * userHeightM);
        int age = (int)((System.currentTimeMillis() / 1000L - user.getDateOfBirth()) / (86400 * 365.25));
        double bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * (age) + (user.getGender() == User.Gender.MALE ? 5 : -161);
        double tdee = bmr * 1.5;

        return new UserStats(imt, bmr, tdee);
    }
}
