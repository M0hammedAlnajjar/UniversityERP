package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.ProgramDTO;
import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.ProgramService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    public ResponseEntity<ProgramDTO> createProgram(@Valid @RequestBody ProgramDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProgramDTO.convertToDTO(programService.createProgram(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<ProgramDTO>> getAllPrograms() {
        return ResponseEntity.ok(ProgramDTO.convertToDTO(programService.getAllPrograms()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDTO> getProgramById(@PathVariable Long id) {
        Program program = programService.getProgramById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + id));
        return ResponseEntity.ok(ProgramDTO.convertToDTO(program));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramDTO> updateProgram(
            @PathVariable Long id,
            @Valid @RequestBody ProgramDTO dto
    ) {
        Program updated = programService.updateProgram(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + id));
        return ResponseEntity.ok(ProgramDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        if (!programService.softDeleteProgram(id)) {
            throw new ResourceNotFoundException("Program not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Program toEntity(ProgramDTO dto) {
        Program program = new Program();
        program.setName(dto.getName());
        program.setDegreeLevel(dto.getDegreeLevel());
        program.setDurationYears(dto.getDurationYears());
        Department department = new Department();
        department.setId(dto.getDepartmentId());
        program.setDepartment(department);
        return program;
    }
}
