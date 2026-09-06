package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Exam;
import com.codelegends.UniversityERP.services.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {
    private final ExamService examService;
    public ExamController(ExamService examService) { this.examService = examService; }
    @PostMapping public ResponseEntity<Exam> createExam(@RequestBody Exam exam) { return ResponseEntity.ok(examService.createExam(exam)); }
    @GetMapping public ResponseEntity<List<Exam>> getAllExams() { return ResponseEntity.ok(examService.getAllExams()); }
    @GetMapping("/{id}") public ResponseEntity<Exam> getExamById(@PathVariable Long id) { return examService.getExamById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Exam> updateExam(@PathVariable Long id, @RequestBody Exam exam) { return examService.updateExam(id, exam).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteExam(@PathVariable Long id) { return examService.softDeleteExam(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
