package ua.rdev.nure.mppzbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.rdev.nure.mppzbackend.exceptions.InvalidSessionException;
import ua.rdev.nure.mppzbackend.responses.ErrorMessageResponse;

@RestControllerAdvice
public class InvalidSessionAdvice {
    @ExceptionHandler(InvalidSessionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessageResponse invalidSessionHandler(InvalidSessionException exc) {
        return new ErrorMessageResponse("Invalid session");
    }
}
