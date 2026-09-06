package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.StudentDTO;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StudentDTO.convertToDTO(studentService.createStudent(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(StudentDTO.convertToDTO(studentService.getAllStudents()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return ResponseEntity.ok(StudentDTO.convertToDTO(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO dto
    ) {
        Student updated = studentService.updateStudent(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return ResponseEntity.ok(StudentDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentService.softDeleteStudent(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Student toEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setGender(dto.getGender());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setMajor(dto.getMajor());
        Program program = new Program();
        program.setId(dto.getProgramId());
        student.setProgram(program);
        return student;
    }
}
