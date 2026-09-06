package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Exam;
import com.codelegends.UniversityERP.repositories.ExamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final CourseService courseService;

    public ExamService(ExamRepository examRepository, CourseService courseService) {
        this.examRepository = examRepository;
        this.courseService = courseService;
    }

    public Exam createExam(Exam exam) {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        if (exam.getTitle() == null || exam.getTitle().isBlank()) {
            throw new IllegalArgumentException("Exam title is required");
        }
        if (exam.getExamDate() == null) {
            throw new IllegalArgumentException("Exam date is required");
        }
        if (!exam.getExamDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Exam date must be in the future");
        }
        if (exam.getTotalMarks() == null || exam.getTotalMarks() <= 0) {
            throw new IllegalArgumentException("Total marks must be greater than zero");
        }
        if (exam.getCourse() == null || exam.getCourse().getId() <= 0) {
            throw new IllegalArgumentException("Course ID is required");
        }

        Course course = courseService.getCourseById(exam.getCourse().getId())
                .orElseThrow(() -> new IllegalArgumentException("Active course not found"));
        exam.setCourse(course);
        exam.setActive(true);
        exam.setCreatedDate(new Date());
        return examRepository.save(exam);
    }

    public List<Exam> getExamsByCourse(Long courseId) {
        courseService.getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Active course not found"));
        return examRepository.findActiveExamsByCourseId(courseId);
    }

    public List<Exam> getAllExams() {
        return examRepository.findAllByIsActiveTrue();
    }

    public Optional<Exam> getExamById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return examRepository.findByIdAndIsActiveTrue(id);
    }

    public Optional<Exam> updateExam(Long id, Exam exam) {
        if (id == null || id <= 0 || exam == null) {
            return Optional.empty();
        }
        Optional<Exam> existingExam = examRepository.findByIdAndIsActiveTrue(id);
        if (existingExam.isEmpty()) {
            return Optional.empty();
        }

        Exam examToUpdate = existingExam.get();
        if (exam.getTitle() == null || exam.getTitle().isBlank()) {
            throw new IllegalArgumentException("Exam title is required");
        }
        if (exam.getExamDate() == null) {
            throw new IllegalArgumentException("Exam date is required");
        }
        if (!exam.getExamDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Exam date must be in the future");
        }
        if (exam.getTotalMarks() == null || exam.getTotalMarks() <= 0) {
            throw new IllegalArgumentException("Total marks must be greater than zero");
        }

        examToUpdate.setTitle(exam.getTitle());
        examToUpdate.setExamDate(exam.getExamDate());
        examToUpdate.setTotalMarks(exam.getTotalMarks());

        if (exam.getCourse() != null && exam.getCourse().getId() > 0) {
            Course course = courseService.getCourseById(exam.getCourse().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Active course not found"));
            examToUpdate.setCourse(course);
        }
        examToUpdate.setUpdatedDate(new Date());
        return Optional.of(examRepository.save(examToUpdate));
    }

    public boolean softDeleteExam(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        Optional<Exam> existingExam = examRepository.findByIdAndIsActiveTrue(id);
        if (existingExam.isEmpty()) {
            return false;
        }
        Exam exam = existingExam.get();
        exam.setActive(false);
        exam.setUpdatedDate(new Date());
        examRepository.save(exam);
        return true;
    }
}
