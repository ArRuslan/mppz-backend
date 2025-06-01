package ua.rdev.nure.mppzbackend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.rdev.nure.mppzbackend.entities.Measurement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementInfo {
    @JsonProperty("timestamp")
    private long timestamp;
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

    public MeasurementInfo(Measurement measurement) {
        timestamp = measurement.getTimestamp();
        restingPulse = measurement.getRestingPulse();
        glucoseLevel = measurement.getGlucoseLevel();
        temperature = measurement.getTemperature();
        sleepDuration = measurement.getSleepDuration();
        stepsCount = measurement.getStepsCount();
        calories = measurement.getCalories();
    }
}
