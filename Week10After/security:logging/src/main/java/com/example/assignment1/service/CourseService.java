package com.example.assignment1.service;

import com.example.assignment1.model.entity.Course;
import com.example.assignment1.model.dto.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CourseService {
    void saveCourse(CourseDto courseDto);
    void deleteById(Long id);
    Page<CourseDto> findAll(Specification<Course> spec, Pageable pageable);
    CourseDto findById(Long id);
    List<CourseDto> findAllById(Iterable<Long> ids);
    void updateCourse(Long id, CourseDto courseDto); // Add this method for updating a course
}

