package com.example.FirstRestApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class MeasurementDTO {

    @Min(value = -100)
    @Max(value = 100)
    private int value;

    @Column(name = "raining")
    private Boolean raining;

    private SensorDTO sensor;


    @Min(value = -100)
    @Max(value = 100)
    public int getValue() {
        return value;
    }

    public void setValue( int value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
