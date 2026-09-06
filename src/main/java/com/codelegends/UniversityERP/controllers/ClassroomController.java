package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Classroom;
import com.codelegends.UniversityERP.services.ClassroomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;
    public ClassroomController(ClassroomService classroomService) { this.classroomService = classroomService; }
    @PostMapping public ResponseEntity<Classroom> createClassroom(@RequestBody Classroom classroom) { return ResponseEntity.ok(classroomService.createClassroom(classroom)); }
    @GetMapping public ResponseEntity<List<Classroom>> getAllClassrooms() { return ResponseEntity.ok(classroomService.getAllClassrooms()); }
    @GetMapping("/{id}") public ResponseEntity<Classroom> getClassroomById(@PathVariable Long id) { return classroomService.getClassroomById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Classroom> updateClassroom(@PathVariable Long id, @RequestBody Classroom classroom) { return classroomService.updateClassroom(id, classroom).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) { return classroomService.softDeleteClassroom(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
