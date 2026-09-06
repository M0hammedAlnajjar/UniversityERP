package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.GuardianDTO;
import com.codelegends.UniversityERP.entities.Guardian;
import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.GuardianService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardians")
public class GuardianController {

    private final GuardianService guardianService;

    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @PostMapping
    public ResponseEntity<GuardianDTO> createGuardian(@Valid @RequestBody GuardianDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GuardianDTO.convertToDTO(guardianService.createGuardian(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<GuardianDTO>> getAllGuardians() {
        return ResponseEntity.ok(GuardianDTO.convertToDTO(guardianService.getAllGuardians()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuardianDTO> getGuardianById(@PathVariable Long id) {
        Guardian guardian = guardianService.getGuardianById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id: " + id));
        return ResponseEntity.ok(GuardianDTO.convertToDTO(guardian));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuardianDTO> updateGuardian(
            @PathVariable Long id,
            @Valid @RequestBody GuardianDTO dto
    ) {
        Guardian updated = guardianService.updateGuardian(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id: " + id));
        return ResponseEntity.ok(GuardianDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuardian(@PathVariable Long id) {
        if (!guardianService.softDeleteGuardian(id)) {
            throw new ResourceNotFoundException("Guardian not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Guardian toEntity(GuardianDTO dto) {
        Guardian guardian = new Guardian();
        guardian.setName(dto.getName());
        guardian.setRelationship(dto.getRelationship());
        guardian.setPhoneNumber(dto.getPhoneNumber());
        Student student = new Student();
        student.setId(dto.getStudentId());
        guardian.setStudent(student);
        return guardian;
    }
}
