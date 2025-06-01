package ua.rdev.nure.mppzbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.rdev.nure.mppzbackend.entities.User;
import ua.rdev.nure.mppzbackend.exceptions.InvalidCredentialsException;
import ua.rdev.nure.mppzbackend.requests.LoginRequest;
import ua.rdev.nure.mppzbackend.requests.RegisterRequest;
import ua.rdev.nure.mppzbackend.responses.AuthResponse;
import ua.rdev.nure.mppzbackend.services.UserService;
import ua.rdev.nure.mppzbackend.utils.JwtTokenUtil;

import static ua.rdev.nure.mppzbackend.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest regBody) {
        User user = userService.register(regBody.getEmail(), regBody.getPassword(), regBody.getFirstName(), regBody.getLastName());
        String token = jwtTokenUtil.generateToken(user);
        return new AuthResponse(token, System.currentTimeMillis() / 1000L + ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    @PostMapping("/login")
    public AuthResponse register(@RequestBody LoginRequest loginReq) {
        User user = userService.findByEmail(loginReq.getEmail()).orElseThrow(InvalidCredentialsException::new);
        if(!passwordEncoder.matches(loginReq.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtTokenUtil.generateToken(user);
        return new AuthResponse(token, System.currentTimeMillis() / 1000L + ACCESS_TOKEN_VALIDITY_SECONDS);
    }
}
