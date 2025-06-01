package ua.rdev.nure.mppzbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.rdev.nure.mppzbackend.entities.User;
import ua.rdev.nure.mppzbackend.exceptions.InvalidSessionException;
import ua.rdev.nure.mppzbackend.responses.UserInfoResponse;
import ua.rdev.nure.mppzbackend.responses.UserStats;
import ua.rdev.nure.mppzbackend.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public UserInfoResponse userInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof User)) {
            throw new InvalidSessionException();
        }

        return new UserInfoResponse((User)principal);
    }

    @GetMapping("/stats")
    public UserStats getState() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof User)) {
            throw new InvalidSessionException();
        }

        return userService.getUserStats((User)principal);
    }
}
