package ua.rdev.nure.mppzbackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidCredentialsException extends RuntimeException {
}
