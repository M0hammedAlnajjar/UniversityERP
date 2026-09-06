package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.FacultyDTO;
import com.codelegends.UniversityERP.entities.Faculty;
import com.codelegends.UniversityERP.entities.University;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.FacultyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<FacultyDTO> createFaculty(@Valid @RequestBody FacultyDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FacultyDTO.convertToDTO(facultyService.createFaculty(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<FacultyDTO>> getAllFaculties() {
        return ResponseEntity.ok(FacultyDTO.convertToDTO(facultyService.getAllFaculties()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id: " + id));
        return ResponseEntity.ok(FacultyDTO.convertToDTO(faculty));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyDTO> updateFaculty(
            @PathVariable Long id,
            @Valid @RequestBody FacultyDTO dto
    ) {
        Faculty updated = facultyService.updateFaculty(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id: " + id));
        return ResponseEntity.ok(FacultyDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        if (!facultyService.softDeleteFaculty(id)) {
            throw new ResourceNotFoundException("Faculty not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Faculty toEntity(FacultyDTO dto) {
        Faculty faculty = new Faculty();
        faculty.setName(dto.getName());
        faculty.setDescription(dto.getDescription());
        University university = new University();
        university.setId(dto.getUniversityId());
        faculty.setUniversity(university);
        return faculty;
    }
}
