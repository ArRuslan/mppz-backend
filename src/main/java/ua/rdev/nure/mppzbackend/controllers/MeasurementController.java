package ua.rdev.nure.mppzbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.rdev.nure.mppzbackend.entities.User;
import ua.rdev.nure.mppzbackend.exceptions.InvalidSessionException;
import ua.rdev.nure.mppzbackend.requests.AddMeasurementRequest;
import ua.rdev.nure.mppzbackend.responses.MeasurementInfo;
import ua.rdev.nure.mppzbackend.services.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("")
    public MeasurementInfo addMeasurement(@RequestBody AddMeasurementRequest req) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof User)) {
            throw new InvalidSessionException();
        }

        return new MeasurementInfo(measurementService.create((User)principal, req));
    }

    @GetMapping("")
    public List<MeasurementInfo> getMeasurements() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof User)) {
            throw new InvalidSessionException();
        }

        return measurementService.getAvgForLastNDays((User)principal, 10);
    }
}
