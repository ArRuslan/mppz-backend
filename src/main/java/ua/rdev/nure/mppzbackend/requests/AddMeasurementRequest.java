package ua.rdev.nure.mppzbackend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMeasurementRequest {
    @JsonProperty("resting_pulse")
    private int restingPulse;
    @JsonProperty("glucose_level")
    private int glucoseLevel;
    private int temperature;
    @JsonProperty("sleep_duration")
    private int sleepDuration;
    @JsonProperty("steps_count")
    private int stepsCount;
    private int calories;
}
