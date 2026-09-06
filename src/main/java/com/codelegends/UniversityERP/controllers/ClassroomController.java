package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.ClassroomDTO;
import com.codelegends.UniversityERP.entities.Classroom;
import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping
    public ResponseEntity<ClassroomDTO> createClassroom(@Valid @RequestBody ClassroomDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClassroomDTO.convertToDTO(classroomService.createClassroom(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<ClassroomDTO>> getAllClassrooms() {
        return ResponseEntity.ok(ClassroomDTO.convertToDTO(classroomService.getAllClassrooms()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable Long id) {
        Classroom classroom = classroomService.getClassroomById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id: " + id));
        return ResponseEntity.ok(ClassroomDTO.convertToDTO(classroom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDTO> updateClassroom(
            @PathVariable Long id,
            @Valid @RequestBody ClassroomDTO dto
    ) {
        Classroom updated = classroomService.updateClassroom(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id: " + id));
        return ResponseEntity.ok(ClassroomDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        if (!classroomService.softDeleteClassroom(id)) {
            throw new ResourceNotFoundException("Classroom not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Classroom toEntity(ClassroomDTO dto) {
        Classroom classroom = new Classroom();
        classroom.setRoomNumber(dto.getRoomNumber());
        classroom.setFloor(dto.getFloor());
        classroom.setCapacity(dto.getCapacity());
        Department department = new Department();
        department.setId(dto.getDepartmentId());
        classroom.setDepartment(department);
        return classroom;
    }
}
