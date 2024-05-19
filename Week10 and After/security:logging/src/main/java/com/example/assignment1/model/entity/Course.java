package com.example.assignment1.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

/**
 * represents course entity in our system.
 * each course can be associated with multiple students as there is a many-to-many relationship.
 *
 */
@Setter
@Getter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    /**
     * default constructor for JPA and Hibernate.
     * it is required for JPA to create an instance of this entity.
     */
    public Course() {

    }
}