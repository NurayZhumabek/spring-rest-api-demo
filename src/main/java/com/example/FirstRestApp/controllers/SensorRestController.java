package com.example.FirstRestApp.controllers;


import com.example.FirstRestApp.dto.SensorDTO;
import com.example.FirstRestApp.models.Sensor;
import com.example.FirstRestApp.services.SensorsService;
import com.example.FirstRestApp.util.SensorErrorResponse;
import com.example.FirstRestApp.util.SensorNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorRestController {

    private final SensorsService sensorsService;

    private final ModelMapper modelMapper;


    @Autowired
    public SensorRestController(SensorsService sensorsService, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Set<SensorDTO> getAllSensors() {
        return sensorsService.findAll().stream()
                .map(this::convertToSensorDTO).collect(Collectors.toSet());
    }


    @PostMapping("/registration")
    public ResponseEntity<SensorDTO> createSensor(@RequestBody @Valid SensorDTO sensor,
                                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errors.append(fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());

        }

        sensorsService.save(convertToSensor(sensor));

        return new ResponseEntity<>(sensor, HttpStatus.CREATED);
    }


    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException ex) {

        SensorErrorResponse response = new SensorErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }


    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }


}
