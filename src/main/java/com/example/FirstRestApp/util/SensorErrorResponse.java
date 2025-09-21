package com.example.FirstRestApp.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class SensorErrorResponse {
    public String message;
    public long timestamp;

    public SensorErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
