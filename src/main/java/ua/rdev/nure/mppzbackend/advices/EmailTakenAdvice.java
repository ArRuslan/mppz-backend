package ua.rdev.nure.mppzbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.rdev.nure.mppzbackend.exceptions.EmailTakenException;
import ua.rdev.nure.mppzbackend.responses.ErrorMessageResponse;

@RestControllerAdvice
public class EmailTakenAdvice {
    @ExceptionHandler(EmailTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResponse emailTakenHandler(EmailTakenException exc) {
        return new ErrorMessageResponse("User with email "+exc.getEmail()+" already exists");
    }
}
