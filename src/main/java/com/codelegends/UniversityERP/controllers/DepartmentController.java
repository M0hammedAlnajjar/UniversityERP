package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.DepartmentDTO;
import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.entities.Faculty;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DepartmentDTO.convertToDTO(departmentService.createDepartment(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(DepartmentDTO.convertToDTO(departmentService.getAllDepartments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return ResponseEntity.ok(DepartmentDTO.convertToDTO(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDTO dto
    ) {
        Department updated = departmentService.updateDepartment(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return ResponseEntity.ok(DepartmentDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (!departmentService.softDeleteDepartment(id)) {
            throw new ResourceNotFoundException("Department not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Department toEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        Faculty faculty = new Faculty();
        faculty.setId(dto.getFacultyId());
        department.setFaculty(faculty);
        return department;
    }
}
