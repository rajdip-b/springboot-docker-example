package com.example.springbootdockerexample.entity;

import com.example.springbootdockerexample.dto.PersonDTO;
import com.example.springbootdockerexample.payload.PersonPayload;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    public Person(PersonPayload payload) {
        this.name = payload.getName();
        this.age = payload.getAge();
        this.email = payload.getEmail();
    }

    public void update(PersonPayload payload) {
        this.name = payload.getName() != null ? payload.getName() : this.name;
        this.age = payload.getAge() != null ? payload.getAge() : this.age;
        this.email = payload.getEmail() != null ? payload.getEmail() : this.email;
    }

    public PersonDTO toDTO() {
        var dto = new PersonDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setAge(this.age);
        dto.setEmail(this.email);
        return dto;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Person person = (Person) o;
        return getId() != null && Objects.equals(getId(), person.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
