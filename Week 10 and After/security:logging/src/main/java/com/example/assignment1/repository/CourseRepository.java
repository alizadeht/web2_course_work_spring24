package com.example.assignment1.repository;

import com.example.assignment1.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Repository interface for -> {@link Course} entities. Furthermore, it extends JpaRepository for CRUD operations and JpaSpecificationExecutor for criteria queries.
 */
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    /**
     * Finds courses by name (exact match)
     *
     * @param name exact name of the course
     * @return list of courses with the specified name
     */
    List<Course> findByName(String name);

    /**
     * Finds courses where the name contains the given string (p.s: it is case-insensitive search)
     *
     * @param name substring to search within course names
     * @return list of courses whose names contain the specified substring
     */
    List<Course> findByNameContainingIgnoreCase(String name);

}
