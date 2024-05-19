package com.example.assignment1.service.impl;

import com.example.assignment1.model.entity.Course;
import com.example.assignment1.model.dto.CourseDto;
import com.example.assignment1.repository.CourseRepository;
import com.example.assignment1.service.CourseService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveCourse(@Valid CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        Course savedCourse = courseRepository.save(course);
        modelMapper.map(savedCourse, CourseDto.class);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Page<CourseDto> findAll(Specification<Course> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable)
                .map(course -> modelMapper.map(course, CourseDto.class));
    }

    @Override
    public CourseDto findById(Long id) {
        return courseRepository.findById(id)
                .map(course -> modelMapper.map(course, CourseDto.class))
                .orElse(null);
    }

    @Override
    public List<CourseDto> findAllById(Iterable<Long> ids) {
        List<Course> courses = courseRepository.findAllById(ids);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public void updateCourse(Long id, CourseDto courseDto) {
        // Find the existing course entity by ID
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();

            // Update the existing course entity with data from the DTO
            existingCourse.setName(courseDto.getName());
            existingCourse.setDescription(courseDto.getDescription());

            // Save the updated course entity
            Course updatedCourse = courseRepository.save(existingCourse);

            // Map the updated course entity to DTO and return
            modelMapper.map(updatedCourse, CourseDto.class);
        } else {
            throw new IllegalArgumentException("Course not found with id: " + id);
        }
    }

}

