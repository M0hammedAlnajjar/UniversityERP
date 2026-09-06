package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Grade;
import com.codelegends.UniversityERP.services.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {
    private final GradeService gradeService;
    public GradeController(GradeService gradeService) { this.gradeService = gradeService; }
    @PostMapping public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) { return ResponseEntity.ok(gradeService.createGrade(grade)); }
    @GetMapping public ResponseEntity<List<Grade>> getAllGrades() { return ResponseEntity.ok(gradeService.getAllGrades()); }
    @GetMapping("/{id}") public ResponseEntity<Grade> getGradeById(@PathVariable Long id) { return gradeService.getGradeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) { return gradeService.updateGrade(id, grade).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteGrade(@PathVariable Long id) { return gradeService.softDeleteGrade(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
