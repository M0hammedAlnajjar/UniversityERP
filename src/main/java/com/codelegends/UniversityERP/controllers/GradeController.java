package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.GradeDTO;
import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.entities.Exam;
import com.codelegends.UniversityERP.entities.Grade;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.services.GradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<GradeDTO> createGrade(@Valid @RequestBody GradeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GradeDTO.convertToDTO(gradeService.createGrade(toEntity(dto))));
    }

    @GetMapping
    public ResponseEntity<List<GradeDTO>> getAllGrades() {
        return ResponseEntity.ok(GradeDTO.convertToDTO(gradeService.getAllGrades()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> getGradeById(@PathVariable Long id) {
        Grade grade = gradeService.getGradeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + id));
        return ResponseEntity.ok(GradeDTO.convertToDTO(grade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> updateGrade(
            @PathVariable Long id,
            @Valid @RequestBody GradeDTO dto
    ) {
        Grade updated = gradeService.updateGrade(id, toEntity(dto))
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + id));
        return ResponseEntity.ok(GradeDTO.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        if (!gradeService.softDeleteGrade(id)) {
            throw new ResourceNotFoundException("Grade not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    private Grade toEntity(GradeDTO dto) {
        Grade grade = new Grade();
        grade.setScore(dto.getScore());
        grade.setLetterGrade(dto.getLetterGrade());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getEnrollmentId());
        grade.setEnrollment(enrollment);
        Exam exam = new Exam();
        exam.setId(dto.getExamId());
        grade.setExam(exam);
        return grade;
    }
}
