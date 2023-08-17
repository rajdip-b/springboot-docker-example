package com.example.springbootdockerexample.service;

import com.example.springbootdockerexample.dto.PersonDTO;
import com.example.springbootdockerexample.exception.EmailExistsException;
import com.example.springbootdockerexample.exception.PersonNotFoundException;
import com.example.springbootdockerexample.payload.PersonPayload;
import org.springframework.data.domain.Page;

public interface PersonService {

    /**
     * Create a new person
     * @param payload The details about the person
     * @return The person DTO
     * @throws EmailExistsException If the email already exists
     */
    PersonDTO createPerson(PersonPayload payload) throws EmailExistsException;

    /**
     * Update a person
     * @param personId The id of the person to update
     * @param payload The details about the person
     * @throws PersonNotFoundException If the person does not exist
     */
    void updatePerson(Long personId, PersonPayload payload) throws PersonNotFoundException;

    /**
     * Delete a person
     * @param personId The id of the person to delete
     * @throws PersonNotFoundException If the person does not exist
     */
    void deletePerson(Long personId) throws PersonNotFoundException;

    /**
     * Get all persons
     * @param page The page number
     * @param size The page size
     * @return A page of persons
     */
    Page<PersonDTO> getAllPersons(Integer page, Integer size);

    /**
     * Get a person by id
     * @param personId The id of the person to get
     * @return The person DTO
     * @throws PersonNotFoundException If the person does not exist
     */
    PersonDTO getPersonById(Long personId) throws PersonNotFoundException;

}
