package com.example.FirstRestApp.util;

import com.example.FirstRestApp.models.Measurement;
import com.example.FirstRestApp.models.Sensor;
import com.example.FirstRestApp.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;


    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (measurement.getSensor() == null || measurement.getSensor().getName() == null) {
            errors.rejectValue("sensor", null, "Sensor is missing or name is null");
            return;
        }

        if (!sensorsService.findByName(measurement.getSensor().getName()).isPresent()) {
            errors.rejectValue("sensor", null, "Sensor with name " + measurement.getSensor().getName() + " not found");
        }


    }
}
