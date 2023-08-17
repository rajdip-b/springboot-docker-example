package com.example.springbootdockerexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
