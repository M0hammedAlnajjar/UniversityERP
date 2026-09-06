package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.services.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService) { this.enrollmentService = enrollmentService; }
    @PostMapping public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) { return ResponseEntity.ok(enrollmentService.createEnrollment(enrollment)); }
    @GetMapping public ResponseEntity<List<Enrollment>> getAllEnrollments() { return ResponseEntity.ok(enrollmentService.getAllEnrollments()); }
    @GetMapping("/{id}") public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) { return enrollmentService.getEnrollmentById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) { return enrollmentService.updateEnrollment(id, enrollment).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) { return enrollmentService.softDeleteEnrollment(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
