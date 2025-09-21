package com.example.FirstRestApp.services;


import com.example.FirstRestApp.models.Sensor;
import com.example.FirstRestApp.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }


    @Transactional
    public Sensor save(Sensor sensor) {
        enrichSensor(sensor);
        return sensorsRepository.save(sensor);
    }


    public Optional<Sensor> findByName(String name) {
        Optional<Sensor> sensor = sensorsRepository.findByName(name);
        return sensor;
    }

    private void enrichSensor(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
        sensor.setUpdatedAt(LocalDateTime.now());
        sensor.setCreatedWho("ADMIN");
    }


}
