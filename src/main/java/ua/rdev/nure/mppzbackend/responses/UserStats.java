package ua.rdev.nure.mppzbackend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStats {
    private double imt;
    private double bmr;
    private double tdee;
}
