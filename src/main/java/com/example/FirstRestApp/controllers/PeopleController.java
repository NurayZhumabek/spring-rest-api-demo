package com.example.FirstRestApp.controllers;


import com.example.FirstRestApp.dto.PersonDTO;
import com.example.FirstRestApp.models.Person;
import com.example.FirstRestApp.services.PeopleServices;
import com.example.FirstRestApp.util.PersonErrorResponse;
import com.example.FirstRestApp.util.PersonNotCreatedException;
import com.example.FirstRestApp.util.PersonNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/people")
public class PeopleController {

    private final PeopleServices peopleServices;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleServices peopleServices, ModelMapper modelMapper) {
        this.peopleServices = peopleServices;
        this.modelMapper = modelMapper;
    }


    @ResponseBody
    @GetMapping
    public List<PersonDTO> people() {
        return peopleServices.findAll().stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }


    @ResponseBody
    @GetMapping("/{id}")
    public PersonDTO peopleById(@PathVariable int id) {
        return convertToPersonDTO(peopleServices.findById(id));
    }


    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody @Valid PersonDTO person,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                errors.append(fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errors.toString());

        }

        peopleServices.save(convertToPerson(person));
        return new ResponseEntity(person, HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {

        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id  was not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {

        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }


    private Person convertToPerson(PersonDTO personDTO) {

        return modelMapper.map(personDTO, Person.class);

    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }


}
