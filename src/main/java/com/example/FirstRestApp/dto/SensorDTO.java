package com.example.FirstRestApp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotNull
    @Size(min = 2, max = 150,message = "Name should be between 2 and 150 characters")
    private String name;


    public @NotNull @Size(min = 2, max = 150, message = "Name should be between 2 and 150 characters") String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2, max = 150, message = "Name should be between 2 and 150 characters") String name) {
        this.name = name;
    }
}
