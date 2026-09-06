package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.InstructorDTO;
import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.entities.Instructor;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InstructorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(InstructorDTO.convertToDTO(instructorService.createInstructor(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        return ResponseEntity.ok(InstructorDTO.convertToDTO(instructorService.getAllInstructors()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable Long id) {
        Instructor instructor = instructorService.getInstructorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
        return ResponseEntity.ok(InstructorDTO.convertToDTO(instructor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(
            @PathVariable Long id,
            @Valid @RequestBody InstructorDTO dto
    ) {
        Instructor updated = instructorService.updateInstructor(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
        return ResponseEntity.ok(InstructorDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        if (!instructorService.softDeleteInstructor(id)) {
            throw new ResourceNotFoundException("Instructor not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Instructor toEntity(InstructorDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setName(dto.getName());
        instructor.setEmail(dto.getEmail());
        instructor.setPhoneNumber(dto.getPhoneNumber());
        instructor.setSpecialization(dto.getSpecialization());
        Department department = new Department();
        department.setId(dto.getDepartmentId());
        instructor.setDepartment(department);
        return instructor;
    }
}
