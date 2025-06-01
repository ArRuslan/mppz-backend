package ua.rdev.nure.mppzbackend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.rdev.nure.mppzbackend.entities.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private User.Gender gender;
    @JsonProperty("date_of_birth")
    private long dateOfBirth;
    private int height;
    private int weight;
}
