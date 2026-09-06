package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.UniversityDTO;
import com.codelegends.UniversityERP.entities.University;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.UniversityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UniversityDTO> createUniversity(@Valid @RequestBody UniversityDTO dto) {
        University university = new University();
        university.setName(dto.getName());
        university.setLocation(dto.getLocation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UniversityDTO.convertToDTO(universityService.createUniversity(university)));
    }

    @GetMapping
    public ResponseEntity<List<UniversityDTO>> getAllUniversities() {
        return ResponseEntity.ok(UniversityDTO.convertToDTO(universityService.getAllUniversities()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDTO> getUniversityById(@PathVariable Long id) {
        University university = universityService.getUniversityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found with id: " + id));
        return ResponseEntity.ok(UniversityDTO.convertToDTO(university));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UniversityDTO> updateUniversity(
            @PathVariable Long id,
            @Valid @RequestBody UniversityDTO dto
    ) {
        University university = new University();
        university.setName(dto.getName());
        university.setLocation(dto.getLocation());
        University updated = universityService.updateUniversity(id, university)
                .orElseThrow(() -> new ResourceNotFoundException("University not found with id: " + id));
        return ResponseEntity.ok(UniversityDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        if (!universityService.softDeleteUniversity(id)) {
            throw new ResourceNotFoundException("University not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
