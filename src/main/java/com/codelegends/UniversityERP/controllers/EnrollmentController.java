package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.EnrollmentDTO;
import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnrollmentDTO.convertToDTO(enrollmentService.createEnrollment(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        return ResponseEntity.ok(EnrollmentDTO.convertToDTO(enrollmentService.getAllEnrollments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        return ResponseEntity.ok(EnrollmentDTO.convertToDTO(enrollment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentDTO dto
    ) {
        Enrollment updated = enrollmentService.updateEnrollment(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        return ResponseEntity.ok(EnrollmentDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        if (!enrollmentService.softDeleteEnrollment(id)) {
            throw new ResourceNotFoundException("Enrollment not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Enrollment toEntity(EnrollmentDTO dto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(dto.getEnrollmentDate());
        enrollment.setStatus(dto.getStatus());
        Student student = new Student();
        student.setId(dto.getStudentId());
        enrollment.setStudent(student);
        Course course = new Course();
        course.setId(dto.getCourseId());
        enrollment.setCourse(course);
        return enrollment;
    }
}
