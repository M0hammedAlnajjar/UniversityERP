package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.University;
import com.codelegends.UniversityERP.services.UniversityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/universities")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @PostMapping
    public ResponseEntity<University> createUniversity(
            @RequestBody University university
    ) {
        return ResponseEntity.ok(
                universityService.createUniversity(university)
        );
    }

    @GetMapping
    public ResponseEntity<List<University>> getAllUniversities() {
        return ResponseEntity.ok(
                universityService.getAllUniversities()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(
            @PathVariable Long id
    ) {
        return universityService.getUniversityById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(
            @PathVariable Long id,
            @RequestBody University university
    ) {
        return universityService.updateUniversity(id, university)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(
            @PathVariable Long id
    ) {
        boolean deleted = universityService.softDeleteUniversity(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
