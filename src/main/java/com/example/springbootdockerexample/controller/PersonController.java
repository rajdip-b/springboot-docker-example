package com.example.springbootdockerexample.controller;

import com.example.springbootdockerexample.exception.EmailExistsException;
import com.example.springbootdockerexample.exception.PersonNotFoundException;
import com.example.springbootdockerexample.payload.PersonPayload;
import com.example.springbootdockerexample.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@Slf4j
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createPerson(@RequestBody PersonPayload payload) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(payload));
        } catch (EmailExistsException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{personId}")
    public ResponseEntity<?> updatePerson(@PathVariable Long personId, @RequestBody PersonPayload payload) {
        try {
            personService.updatePerson(personId, payload);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PersonNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long personId) {
        try {
            personService.deletePerson(personId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PersonNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{personId}")
    public ResponseEntity<?> getPersonById(@PathVariable Long personId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonById(personId));
        } catch (PersonNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPersons(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.getAllPersons(pageNo, pageSize));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
