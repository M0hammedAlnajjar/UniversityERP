package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.dto.StudentPerformanceDTO;
import com.codelegends.UniversityERP.dto.TopStudentDTO;
import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.entities.Exam;
import com.codelegends.UniversityERP.entities.Grade;
import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.repositories.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeService {

    private final GradeRepository gradeRepository;
    private final EnrollmentService enrollmentService;
    private final ExamService examService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final ProgramService programService;

    public GradeService(
            GradeRepository gradeRepository,
            EnrollmentService enrollmentService,
            ExamService examService,
            StudentService studentService,
            CourseService courseService,
            ProgramService programService
    ) {
        this.gradeRepository = gradeRepository;
        this.enrollmentService = enrollmentService;
        this.examService = examService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.programService = programService;
    }

    public Grade createGrade(Grade grade) {
        if (grade == null) throw new IllegalArgumentException("Grade cannot be null");
        validateBasicGrade(grade);

        Enrollment enrollment = enrollmentService.getEnrollmentById(grade.getEnrollment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active enrollment not found"));
        Exam exam = examService.getExamById(grade.getExam().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active exam not found"));

        validateEnrollmentExamAndScore(enrollment, exam, grade.getScore());
        if (gradeRepository.existsByEnrollment_IdAndExam_IdAndIsActiveTrue(enrollment.getId(), exam.getId())) {
            throw new IllegalArgumentException("A grade already exists for this enrollment and exam");
        }

        grade.setEnrollment(enrollment);
        grade.setExam(exam);
        grade.setActive(true);
        grade.setCreatedDate(new Date());
        return gradeRepository.save(grade);
    }

    public StudentPerformanceDTO getStudentPerformance(Long studentId) {
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Active student not found"));
        List<Grade> grades = gradeRepository.findActiveGradesByStudentId(studentId);
        if (grades.isEmpty()) {
            return StudentPerformanceDTO.builder()
                    .studentId(student.getId())
                    .studentName(student.getName())
                    .averageScore(0.0)
                    .averagePercentage(0.0)
                    .gpa(0.0)
                    .classification("No Grades")
                    .build();
        }

        double averageScore = grades.stream().mapToDouble(Grade::getScore).average().orElse(0.0);
        double averagePercentage = grades.stream()
                .mapToDouble(g -> (g.getScore() / g.getExam().getTotalMarks()) * 100.0)
                .average()
                .orElse(0.0);

        return StudentPerformanceDTO.builder()
                .studentId(student.getId())
                .studentName(student.getName())
                .averageScore(round(averageScore))
                .averagePercentage(round(averagePercentage))
                .gpa(gpaFromPercentage(averagePercentage))
                .classification(classificationFromPercentage(averagePercentage))
                .build();
    }

    public Double getCourseAverageScore(Long courseId) {
        courseService.getCourseById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Active course not found"));
        Double average = gradeRepository.findAverageScoreByCourseId(courseId);
        return average == null ? 0.0 : round(average);
    }

    public Double getProgramAverageScore(Long programId) {
        programService.getProgramById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Active program not found"));
        Double average = gradeRepository.findAverageScoreByProgramId(programId);
        return average == null ? 0.0 : round(average);
    }

    public Double getProgramAverageGpa(Long programId) {
        programService.getProgramById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Active program not found"));
        List<Grade> grades = gradeRepository.findActiveGradesByProgramId(programId);
        if (grades.isEmpty()) return 0.0;
        double averageGpa = grades.stream()
                .mapToDouble(g -> gpaFromPercentage((g.getScore() / g.getExam().getTotalMarks()) * 100.0))
                .average()
                .orElse(0.0);
        return round(averageGpa);
    }

    public TopStudentDTO getTopStudentByProgram(Long programId) {
        programService.getProgramById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Active program not found"));
        List<Object[]> ranking = gradeRepository.findStudentAverageRankingByProgramId(programId);
        if (ranking.isEmpty()) {
            return TopStudentDTO.builder().programId(programId).averageScore(0.0).build();
        }
        Object[] row = ranking.getFirst();
        return TopStudentDTO.builder()
                .programId(programId)
                .studentId(((Number) row[0]).longValue())
                .studentName((String) row[1])
                .averageScore(round(((Number) row[2]).doubleValue()))
                .build();
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAllByIsActiveTrue();
    }

    public Optional<Grade> getGradeById(Long id) {
        if (id == null || id <= 0) return Optional.empty();
        return gradeRepository.findByIdAndIsActiveTrue(id);
    }

    public Optional<Grade> updateGrade(Long id, Grade grade) {
        if (id == null || id <= 0 || grade == null) return Optional.empty();
        Optional<Grade> existingGrade = gradeRepository.findByIdAndIsActiveTrue(id);
        if (existingGrade.isEmpty()) return Optional.empty();
        validateBasicGrade(grade);

        Enrollment enrollment = enrollmentService.getEnrollmentById(grade.getEnrollment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active enrollment not found"));
        Exam exam = examService.getExamById(grade.getExam().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active exam not found"));
        validateEnrollmentExamAndScore(enrollment, exam, grade.getScore());

        Grade gradeToUpdate = existingGrade.get();
        gradeToUpdate.setScore(grade.getScore());
        gradeToUpdate.setLetterGrade(grade.getLetterGrade());
        gradeToUpdate.setEnrollment(enrollment);
        gradeToUpdate.setExam(exam);
        gradeToUpdate.setUpdatedDate(new Date());
        return Optional.of(gradeRepository.save(gradeToUpdate));
    }

    public boolean softDeleteGrade(Long id) {
        if (id == null || id <= 0) return false;
        Optional<Grade> existingGrade = gradeRepository.findByIdAndIsActiveTrue(id);
        if (existingGrade.isEmpty()) return false;
        Grade grade = existingGrade.get();
        grade.setActive(false);
        grade.setUpdatedDate(new Date());
        gradeRepository.save(grade);
        return true;
    }

    private void validateBasicGrade(Grade grade) {
        if (grade.getScore() == null || grade.getScore() < 0) throw new IllegalArgumentException("Grade score must be zero or greater");
        if (grade.getLetterGrade() == null || grade.getLetterGrade().isBlank()) throw new IllegalArgumentException("Letter grade is required");
        if (grade.getEnrollment() == null || grade.getEnrollment().getId() <= 0) throw new IllegalArgumentException("Enrollment ID is required");
        if (grade.getExam() == null || grade.getExam().getId() <= 0) throw new IllegalArgumentException("Exam ID is required");
    }

    private void validateEnrollmentExamAndScore(Enrollment enrollment, Exam exam, Double score) {
        if (enrollment.getCourse().getId() != exam.getCourse().getId()) {
            throw new IllegalArgumentException("Exam does not belong to the enrollment course");
        }
        if (score > exam.getTotalMarks()) {
            throw new IllegalArgumentException("Grade score cannot exceed exam total marks");
        }
    }

    private double gpaFromPercentage(double percentage) {
        if (percentage >= 90) return 4.0;
        if (percentage >= 80) return 3.0;
        if (percentage >= 70) return 2.0;
        if (percentage >= 60) return 1.0;
        return 0.0;
    }

    private String classificationFromPercentage(double percentage) {
        if (percentage >= 90) return "Excellent";
        if (percentage >= 80) return "Very Good";
        if (percentage >= 70) return "Good";
        if (percentage >= 60) return "Pass";
        return "Fail";
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
