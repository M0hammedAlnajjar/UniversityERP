package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) { this.studentService = studentService; }
    @PostMapping public ResponseEntity<Student> createStudent(@RequestBody Student student) { return ResponseEntity.ok(studentService.createStudent(student)); }
    @GetMapping public ResponseEntity<List<Student>> getAllStudents() { return ResponseEntity.ok(studentService.getAllStudents()); }
    @GetMapping("/{id}") public ResponseEntity<Student> getStudentById(@PathVariable Long id) { return studentService.getStudentById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) { return studentService.updateStudent(id, student).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteStudent(@PathVariable Long id) { return studentService.softDeleteStudent(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
