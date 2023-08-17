package com.example.springbootdockerexample.repository;

import com.example.springbootdockerexample.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

}
