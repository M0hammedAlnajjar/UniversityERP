package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Instructor;
import com.codelegends.UniversityERP.services.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {
    private final InstructorService instructorService;
    public InstructorController(InstructorService instructorService) { this.instructorService = instructorService; }
    @PostMapping public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) { return ResponseEntity.ok(instructorService.createInstructor(instructor)); }
    @GetMapping public ResponseEntity<List<Instructor>> getAllInstructors() { return ResponseEntity.ok(instructorService.getAllInstructors()); }
    @GetMapping("/{id}") public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id) { return instructorService.getInstructorById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor) { return instructorService.updateInstructor(id, instructor).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) { return instructorService.softDeleteInstructor(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
