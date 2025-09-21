package com.example.FirstRestApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PersonDTO {


    @NotEmpty(message = "The field full name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String fullName;

    private int yearOfBirth;


    public @NotEmpty(message = "The field full name should not be empty") @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") String getFullName() {
        return fullName;
    }

    public void setFullName(@NotEmpty(message = "The field full name should not be empty") @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") String fullName) {
        this.fullName = fullName;
    }


}
