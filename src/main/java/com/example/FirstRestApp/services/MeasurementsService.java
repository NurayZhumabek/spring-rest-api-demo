package com.example.FirstRestApp.services;


import com.example.FirstRestApp.models.Measurement;
import com.example.FirstRestApp.models.Sensor;
import com.example.FirstRestApp.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }


    @Transactional
    public Measurement save(Measurement measurement) {
        Sensor sensor = sensorsService.findByName(measurement.getSensor().getName())
                .orElseThrow(() -> new RuntimeException("Sensor not found"));

        measurement.setSensor(sensor); //
        enrichMeasurements(measurement);
        return measurementsRepository.save(measurement);
    }


    public int countOfRainyDays() {
        return measurementsRepository.countByRaining(true);
    }

    public List<Integer> getMeasurementValues() {
        return measurementsRepository.getMeasurementByValue();
    }

    public void enrichMeasurements(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
        measurement.setUpdatedAt(LocalDateTime.now());
        measurement.setCreatedWho("ADMIN");
    }


}
