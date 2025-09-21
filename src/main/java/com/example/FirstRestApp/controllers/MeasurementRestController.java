package com.example.FirstRestApp.controllers;


import com.example.FirstRestApp.dto.MeasurementDTO;
import com.example.FirstRestApp.models.Measurement;
import com.example.FirstRestApp.services.MeasurementsService;
import com.example.FirstRestApp.util.MeasurementErrorResponse;
import com.example.FirstRestApp.util.MeasurementNotAddedException;
import com.example.FirstRestApp.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementRestController {


    private final MeasurementsService measurementsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;


    @Autowired
    public MeasurementRestController(MeasurementsService measurementsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> getAllMeasurements() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public String getRainyDaysCount() {

        return "The count of rainy days:" + measurementsService.countOfRainyDays();
    }

    @GetMapping("/values")
    public List<Integer> getMeasurementsValues() {
        return measurementsService.getMeasurementValues();
    }


    @PostMapping("/add")
    public ResponseEntity<MeasurementDTO> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                         BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        sensorValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("; ")
            );
            throw new MeasurementNotAddedException(errors.toString());
        }

        measurementsService.save(measurement);
        return new ResponseEntity<>(measurementDTO, HttpStatus.CREATED);
    }






    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }



    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddedException ex) {
        MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }



}
