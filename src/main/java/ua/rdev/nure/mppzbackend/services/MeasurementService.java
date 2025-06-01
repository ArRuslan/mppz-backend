package ua.rdev.nure.mppzbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rdev.nure.mppzbackend.entities.Measurement;
import ua.rdev.nure.mppzbackend.entities.User;
import ua.rdev.nure.mppzbackend.repositories.MeasurementRepository;
import ua.rdev.nure.mppzbackend.requests.AddMeasurementRequest;
import ua.rdev.nure.mppzbackend.responses.MeasurementInfo;

import java.util.*;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Measurement create(User user, AddMeasurementRequest req) {
        Measurement measurement = new Measurement();

        measurement.setTimestamp(System.currentTimeMillis() / 1000L);
        measurement.setRestingPulse(req.getRestingPulse());
        measurement.setGlucoseLevel(req.getGlucoseLevel());
        measurement.setTemperature(req.getTemperature());
        measurement.setSleepDuration(req.getSleepDuration());
        measurement.setStepsCount(req.getStepsCount());
        measurement.setCalories(req.getCalories());
        measurement.setUser(user);

        return measurementRepository.save(measurement);
    }

    public List<MeasurementInfo> getAvgForLastNDays(User user, int days) {
        List<Measurement> measurements = measurementRepository.findAllByUserAndTimestampAfter(user, days * 86400L);
        Map<Long, MeasurementInfo> avgs = new HashMap<>();
        Map<Long, Long> counts = new HashMap<>();

        for(Measurement measurement : measurements) {
            long day = measurement.getTimestamp() / 86400L;
            MeasurementInfo avg;
            if(avgs.containsKey(day) && counts.containsKey(day)) {
                avg = avgs.get(day);
            } else {
                avgs.put(day, avg = new MeasurementInfo());
            }

            avg.setRestingPulse(avg.getRestingPulse() + measurement.getRestingPulse());
            avg.setGlucoseLevel(avg.getGlucoseLevel() + measurement.getGlucoseLevel());
            avg.setTemperature(avg.getTemperature() + measurement.getTemperature());
            avg.setSleepDuration(avg.getSleepDuration() + measurement.getSleepDuration());
            avg.setStepsCount(avg.getStepsCount() + measurement.getStepsCount());
            avg.setCalories(avg.getCalories() + measurement.getCalories());

            counts.put(day, counts.getOrDefault(day, 0L) + 1);
        }

        List<MeasurementInfo> result = new ArrayList<>(avgs.size());
        for(Map.Entry<Long, MeasurementInfo> entry : avgs.entrySet()) {
            long day = entry.getKey();
            long count = counts.get(day);
            MeasurementInfo avg = entry.getValue();

            result.add(new MeasurementInfo(
                    day * 86400L,
                    (int)(avg.getRestingPulse() / count),
                    (int)(avg.getGlucoseLevel() / count),
                    (int)(avg.getTemperature() / count),
                    (int)(avg.getSleepDuration() / count),
                    (int)(avg.getStepsCount() / count),
                    (int)(avg.getCalories() / count)
            ));
        }

        result.sort(Comparator.comparing(MeasurementInfo::getTimestamp));
        return result;
    }
}
