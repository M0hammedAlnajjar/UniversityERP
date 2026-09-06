package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.ExamDTO;
import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Exam;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@Valid @RequestBody ExamDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ExamDTO.convertToDTO(examService.createExam(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        return ResponseEntity.ok(ExamDTO.convertToDTO(examService.getAllExams()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable Long id) {
        Exam exam = examService.getExamById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
        return ResponseEntity.ok(ExamDTO.convertToDTO(exam));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(
            @PathVariable Long id,
            @Valid @RequestBody ExamDTO dto
    ) {
        Exam updated = examService.updateExam(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
        return ResponseEntity.ok(ExamDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        if (!examService.softDeleteExam(id)) {
            throw new ResourceNotFoundException("Exam not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Exam toEntity(ExamDTO dto) {
        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setExamDate(dto.getExamDate());
        exam.setTotalMarks(dto.getTotalMarks());
        Course course = new Course();
        course.setId(dto.getCourseId());
        exam.setCourse(course);
        return exam;
    }
}
