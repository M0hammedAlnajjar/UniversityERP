package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.*;
import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.entities.Exam;
import com.codelegends.UniversityERP.entities.Grade;
import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.enums.EnrollmentStatus;
import com.codelegends.UniversityERP.services.CourseService;
import com.codelegends.UniversityERP.services.EnrollmentService;
import com.codelegends.UniversityERP.services.ExamService;
import com.codelegends.UniversityERP.services.GradeService;
import com.codelegends.UniversityERP.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final ExamService examService;
    private final GradeService gradeService;
    private final StudentService studentService;

    public BusinessController(
            EnrollmentService enrollmentService,
            CourseService courseService,
            ExamService examService,
            GradeService gradeService,
            StudentService studentService
    ) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.examService = examService;
        this.gradeService = gradeService;
        this.studentService = studentService;
    }

    @PostMapping("/enrollments")
    public ResponseEntity<EnrollmentDTO> enrollStudent(
            @RequestParam Long studentId,
            @RequestParam Long courseId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnrollmentDTO.convertToDTO(enrollmentService.enrollStudent(studentId, courseId)));
    }

    @PatchMapping("/enrollments/{id}/drop")
    public ResponseEntity<EnrollmentDTO> dropEnrollment(@PathVariable Long id) {
        return ResponseEntity.ok(EnrollmentDTO.convertToDTO(enrollmentService.dropEnrollment(id)));
    }

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<CourseDTO>> getStudentCourses(@PathVariable Long studentId) {
        return ResponseEntity.ok(CourseDTO.convertToDTO(enrollmentService.getCoursesForStudent(studentId)));
    }

    @PutMapping("/courses/{courseId}/instructor/{instructorId}")
    public ResponseEntity<CourseDTO> assignInstructor(
            @PathVariable Long courseId,
            @PathVariable Long instructorId
    ) {
        return ResponseEntity.ok(CourseDTO.convertToDTO(courseService.assignInstructor(courseId, instructorId)));
    }

    @GetMapping("/instructors/{instructorId}/courses")
    public ResponseEntity<List<CourseDTO>> getInstructorCourses(@PathVariable Long instructorId) {
        return ResponseEntity.ok(CourseDTO.convertToDTO(courseService.getCoursesByInstructor(instructorId)));
    }

    @PostMapping("/courses/{courseId}/exams")
    public ResponseEntity<ExamDTO> scheduleExam(
            @PathVariable Long courseId,
            @Valid @RequestBody ExamScheduleDTO request
    ) {
        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setExamDate(request.getExamDate());
        exam.setTotalMarks(request.getTotalMarks());
        Course course = new Course();
        course.setId(courseId);
        exam.setCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ExamDTO.convertToDTO(examService.createExam(exam)));
    }

    @GetMapping("/courses/{courseId}/exams")
    public ResponseEntity<List<ExamDTO>> getCourseExams(@PathVariable Long courseId) {
        return ResponseEntity.ok(ExamDTO.convertToDTO(examService.getExamsByCourse(courseId)));
    }

    @PostMapping("/enrollments/{enrollmentId}/exams/{examId}/grade")
    public ResponseEntity<GradeDTO> recordGrade(
            @PathVariable Long enrollmentId,
            @PathVariable Long examId,
            @Valid @RequestBody GradeRecordDTO request
    ) {
        Grade grade = new Grade();
        grade.setScore(request.getScore());
        grade.setLetterGrade(request.getLetterGrade());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);
        grade.setEnrollment(enrollment);
        Exam exam = new Exam();
        exam.setId(examId);
        grade.setExam(exam);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GradeDTO.convertToDTO(gradeService.createGrade(grade)));
    }

    @GetMapping("/students/{studentId}/performance")
    public ResponseEntity<StudentPerformanceDTO> getStudentPerformance(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getStudentPerformance(studentId));
    }

    @GetMapping("/programs/{programId}/courses")
    public ResponseEntity<List<CourseDTO>> getProgramCourses(@PathVariable Long programId) {
        return ResponseEntity.ok(CourseDTO.convertToDTO(courseService.getCoursesByProgram(programId)));
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<StudentDTO>> getCourseStudents(@PathVariable Long courseId) {
        List<Student> students = studentService.getStudentsByCourse(courseId);
        return ResponseEntity.ok(StudentDTO.convertToDTO(students));
    }

    @GetMapping("/enrollments/status/{status}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStatus(@PathVariable EnrollmentStatus status) {
        return ResponseEntity.ok(EnrollmentDTO.convertToDTO(enrollmentService.getEnrollmentsByStatus(status)));
    }

    @GetMapping("/courses/without-instructor")
    public ResponseEntity<List<CourseDTO>> getCoursesWithoutInstructor() {
        return ResponseEntity.ok(CourseDTO.convertToDTO(courseService.getCoursesWithoutInstructor()));
    }

    @GetMapping("/courses/{courseId}/average-score")
    public ResponseEntity<MetricDTO> getCourseAverageScore(@PathVariable Long courseId) {
        return ResponseEntity.ok(MetricDTO.builder()
                .entityId(courseId)
                .metric("averageExamScore")
                .value(gradeService.getCourseAverageScore(courseId))
                .build());
    }

    @GetMapping("/programs/{programId}/top-student")
    public ResponseEntity<TopStudentDTO> getTopStudent(@PathVariable Long programId) {
        return ResponseEntity.ok(gradeService.getTopStudentByProgram(programId));
    }

    @GetMapping("/courses/no-enrollments")
    public ResponseEntity<List<CourseDTO>> getCoursesWithNoEnrollments() {
        return ResponseEntity.ok(CourseDTO.convertToDTO(courseService.getCoursesWithNoEnrollments()));
    }
}
