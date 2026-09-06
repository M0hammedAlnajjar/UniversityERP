package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.CourseDTO;
import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Instructor;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CourseDTO.convertToDTO(courseService.createCourse(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(CourseDTO.convertToDTO(courseService.getAllCourses()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return ResponseEntity.ok(CourseDTO.convertToDTO(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDTO dto
    ) {
        Course updated = courseService.updateCourse(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return ResponseEntity.ok(CourseDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.softDeleteCourse(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Course toEntity(CourseDTO dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setCourseCode(dto.getCourseCode());
        course.setCreditHours(dto.getCreditHours());
        Program program = new Program();
        program.setId(dto.getProgramId());
        course.setProgram(program);
        if (dto.getInstructorId() != null) {
            Instructor instructor = new Instructor();
            instructor.setId(dto.getInstructorId());
            course.setInstructor(instructor);
        }
        return course;
    }
}
