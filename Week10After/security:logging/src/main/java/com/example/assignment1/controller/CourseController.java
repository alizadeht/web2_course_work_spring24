package com.example.assignment1.controller;

import com.example.assignment1.model.entity.Course;
import com.example.assignment1.model.dto.CourseDto;
import com.example.assignment1.service.CourseService;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing courses.
 */
@Controller
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private static final Logger updateLogger = LoggerFactory.getLogger("UpdateCourse");

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Displays a paginated list of courses.
     *
     * @param model       The Model object for passing data to the view.
     * @param pageable    The pageable object for pagination.
     * @param nameFilter  Filter for course name.
     * @param idFilter    Filter for course ID.
     * @return The view name.
     */
    @GetMapping("/courses")
    public String listCourses(Model model, @PageableDefault(size = 5) Pageable pageable,
                              @RequestParam(required = false) String nameFilter,
                              @RequestParam(required = false) String idFilter) {
        logger.info("Listing courses...");
        Specification<Course> spec = createCourseSpecification(nameFilter, idFilter);
        model.addAttribute("coursesPage", courseService.findAll(spec, pageable));
        return "courses";
    }

    /**
     * Creates a specification based on the provided name and ID filters.
     *
     * @param nameFilter The name filter.
     * @param idFilter   The ID filter.
     * @return The Specification object.
     */

    private Specification<Course> createCourseSpecification(String nameFilter, String idFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nameFilter != null && !nameFilter.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + nameFilter.toLowerCase() + "%"));
            }
            if (idFilter != null && !idFilter.trim().isEmpty()) {
                try {
                    Long id = Long.parseLong(idFilter.trim());
                    predicates.add(criteriaBuilder.equal(root.get("id"), id));
                } catch (NumberFormatException e) {
                    logger.warn("Failed to parse 'idFilter' to Long: {}", idFilter);
                    return criteriaBuilder.disjunction();
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    /**
     * Displays the form for adding a new course.
     *
     * @param model The Model object for passing data to the view.
     * @return The view name.
     */
    @GetMapping("/courses/new")
    public String showCourseForm(Model model) {
        model.addAttribute("course", new CourseDto());
        return "course-form";
    }
    /**
     * Saves a new course.
     *
     * @param courseDto          The CourseDto object containing course data.
     * @param result             The BindingResult object for validation.
     * @param redirectAttributes The RedirectAttributes object for flash messages.
     * @param model              The Model object for passing data to the view.
     * @return The redirection URL.
     */
    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute("course") @Valid CourseDto courseDto, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("course", courseDto);
            return "course-form";  // Return to the form page if there are validation errors
        }
        courseService.saveCourse(courseDto);
        redirectAttributes.addFlashAttribute("successMessage", "Course saved successfully!");
        return "redirect:/courses";
    }


    @GetMapping("/courses/edit/{id}")
    public String showEditCourseForm(@PathVariable Long id, Model model) {
        logger.info("Showing edit course form for course with ID: {}", id);
        CourseDto courseDto = courseService.findById(id);
        if (courseDto == null) {
            throw new IllegalArgumentException("Invalid course Id:" + id);
        }
        model.addAttribute("course", courseDto);
        return "course-edit";
    }

    @PostMapping("/courses/update/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute("course") @Valid CourseDto courseDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("course", courseDto);
            return "course-edit";  // Stay on the edit form if there are errors
        }
        updateLogger.info("Updating course with ID: {}", id);
        courseService.updateCourse(id, courseDto); // Update the course
        redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");
        return "redirect:/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully.");
        return "redirect:/courses";
    }

    @PostMapping("/courses/save")
    public String saveCourseAfterEdit(@ModelAttribute("course") CourseDto courseDto, RedirectAttributes redirectAttributes) {
        courseService.saveCourse(courseDto);
        redirectAttributes.addFlashAttribute("successMessage", "Course saved successfully!");
        return "redirect:/courses";
    }
}
