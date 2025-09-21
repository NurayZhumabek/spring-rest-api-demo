package com.example.FirstRestApp.util;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MeasurementErrorResponse {

    private String message;
    private long timestamp;


    public MeasurementErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
