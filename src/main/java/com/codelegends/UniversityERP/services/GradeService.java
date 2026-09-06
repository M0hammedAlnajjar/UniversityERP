package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.entities.Exam;
import com.codelegends.UniversityERP.entities.Grade;
import com.codelegends.UniversityERP.repositories.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final EnrollmentService enrollmentService;
    private final ExamService examService;

    public GradeService(
            GradeRepository gradeRepository,
            EnrollmentService enrollmentService,
            ExamService examService
    ) {
        this.gradeRepository = gradeRepository;
        this.enrollmentService = enrollmentService;
        this.examService = examService;
    }

    public Grade createGrade(Grade grade) {

        if (grade == null) {
            throw new IllegalArgumentException(
                    "Grade cannot be null"
            );
        }

        if (grade.getScore() == null
                || grade.getScore() < 0) {
            throw new IllegalArgumentException(
                    "Grade score must be zero or greater"
            );
        }

        if (grade.getLetterGrade() == null
                || grade.getLetterGrade().isBlank()) {
            throw new IllegalArgumentException(
                    "Letter grade is required"
            );
        }

        if (grade.getEnrollment() == null
                || grade.getEnrollment().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Enrollment ID is required"
            );
        }

        Enrollment enrollment = enrollmentService
                .getEnrollmentById(
                        grade.getEnrollment().getId()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active enrollment not found"
                        )
                );

        if (grade.getExam() == null
                || grade.getExam().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Exam ID is required"
            );
        }

        Exam exam = examService
                .getExamById(
                        grade.getExam().getId()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active exam not found"
                        )
                );

        grade.setEnrollment(enrollment);
        grade.setExam(exam);
        grade.setActive(true);
        grade.setCreatedDate(new Date());

        return gradeRepository.save(grade);
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAllByIsActiveTrue();
    }
}
