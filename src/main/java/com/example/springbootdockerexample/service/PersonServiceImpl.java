package com.example.springbootdockerexample.service;

import com.example.springbootdockerexample.entity.Person;
import com.example.springbootdockerexample.dto.PersonDTO;
import com.example.springbootdockerexample.exception.EmailExistsException;
import com.example.springbootdockerexample.exception.PersonNotFoundException;
import com.example.springbootdockerexample.repository.PersonRepository;
import com.example.springbootdockerexample.payload.PersonPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public PersonDTO createPerson(PersonPayload payload) throws EmailExistsException {
        // Check if email already exists
        if (personRepository.existsByEmail(payload.getEmail())) {
            throw new EmailExistsException(payload.getEmail());
        }

        // Else, create a new person
        var person = new Person(payload);
        person = personRepository.save(person);
        log.info("Created person: {}", person);

        // Return the DTO
        return person.toDTO();
    }

    @Override
    @Transactional
    public void updatePerson(Long personId, PersonPayload payload) throws PersonNotFoundException {
        // Get the person by id
        var person = getById(personId);

        // Update the person
        person.update(payload);

        // Save the person
        personRepository.save(person);

        log.info("Updated person: {}", person);
    }

    @Override
    @Transactional
    public void deletePerson(Long personId) throws PersonNotFoundException {
        // Get the person by id
        var person = getById(personId);

        // Delete the person
        personRepository.delete(person);

        log.info("Deleted person: {}", person);
    }

    @Override
    public Page<PersonDTO> getAllPersons(Integer page, Integer size) {
        return personRepository
                .findAll(PageRequest.of(page, size))
                .map(Person::toDTO);
    }

    @Override
    public PersonDTO getPersonById(Long personId) throws PersonNotFoundException {
        return getById(personId).toDTO();
    }

    /**
     * Internal helper function to fetch a person by id
     * @param personId the id of the person to fetch
     * @return the person
     * @throws PersonNotFoundException if the person is not found
     */
    private Person getById(Long personId) throws PersonNotFoundException {
    return personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));
    }
}
