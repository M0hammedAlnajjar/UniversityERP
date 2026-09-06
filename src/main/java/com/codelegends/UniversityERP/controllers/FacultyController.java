package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Faculty;
import com.codelegends.UniversityERP.services.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(
            @RequestBody Faculty faculty
    ) {
        return ResponseEntity.ok(
                facultyService.createFaculty(faculty)
        );
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(
                facultyService.getAllFaculties()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(
            @PathVariable Long id
    ) {
        return facultyService.getFacultyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(
            @PathVariable Long id,
            @RequestBody Faculty faculty
    ) {
        return facultyService.updateFaculty(id, faculty)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(
            @PathVariable Long id
    ) {
        boolean deleted = facultyService.softDeleteFaculty(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
