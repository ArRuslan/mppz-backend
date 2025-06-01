package ua.rdev.nure.mppzbackend.responses;

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
public class UserInfoResponse {
    private long id;
    private String email;
    private String name;
    private String nickname;
    private User.Gender gender;
    @JsonProperty("date_of_birth")
    private long dateOfBirth;
    private int height;
    private int weight;

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.height = user.getHeight();
        this.weight = user.getWeight();
    }
}
