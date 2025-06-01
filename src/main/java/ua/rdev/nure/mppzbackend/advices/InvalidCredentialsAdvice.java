package ua.rdev.nure.mppzbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.rdev.nure.mppzbackend.exceptions.InvalidCredentialsException;
import ua.rdev.nure.mppzbackend.responses.ErrorMessageResponse;

@RestControllerAdvice
public class InvalidCredentialsAdvice {
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResponse invalidSessionHandler(InvalidCredentialsException exc) {
        return new ErrorMessageResponse("User with this credentials does not exist");
    }
}
