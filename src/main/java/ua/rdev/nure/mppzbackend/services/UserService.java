package ua.rdev.nure.mppzbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.rdev.nure.mppzbackend.entities.User;
import ua.rdev.nure.mppzbackend.exceptions.EmailTakenException;
import ua.rdev.nure.mppzbackend.repositories.UserRepository;
import ua.rdev.nure.mppzbackend.requests.RegisterRequest;

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
}
