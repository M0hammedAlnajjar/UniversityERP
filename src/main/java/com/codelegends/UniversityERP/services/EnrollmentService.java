package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.enums.EnrollmentStatus;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.repositories.ClassroomRepository;
import com.codelegends.UniversityERP.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            ClassroomRepository classroomRepository,
            StudentService studentService,
            CourseService courseService
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.classroomRepository = classroomRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null");
        }
        if (enrollment.getStudent() == null || enrollment.getStudent().getId() <= 0) {
            throw new IllegalArgumentException("Student ID is required");
        }
        if (enrollment.getCourse() == null || enrollment.getCourse().getId() <= 0) {
            throw new IllegalArgumentException("Course ID is required");
        }

        Student student = studentService.getStudentById(enrollment.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active student not found"));
        Course course = courseService.getCourseById(enrollment.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active course not found"));

        if (enrollmentRepository.existsByStudent_IdAndCourse_IdAndStatusAndIsActiveTrue(
                student.getId(), course.getId(), EnrollmentStatus.ENROLLED)) {
            throw new IllegalArgumentException("Student is already enrolled in this course");
        }

        validateCourseCapacity(course);

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(enrollment.getEnrollmentDate() == null ? LocalDate.now() : enrollment.getEnrollmentDate());
        enrollment.setStatus(enrollment.getStatus() == null ? EnrollmentStatus.ENROLLED : enrollment.getStatus());
        enrollment.setActive(true);
        enrollment.setCreatedDate(new Date());
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment enrollStudent(Long studentId, Long courseId) {
        if (studentId == null || studentId <= 0 || courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Valid student and course IDs are required");
        }
        Enrollment enrollment = new Enrollment();
        Student student = new Student();
        student.setId(studentId);
        Course course = new Course();
        course.setId(courseId);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        return createEnrollment(enrollment);
    }

    public Enrollment dropEnrollment(Long id) {
        Enrollment enrollment = getEnrollmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Active enrollment not found"));
        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollment.setUpdatedDate(new Date());
        return enrollmentRepository.save(enrollment);
    }

    public List<Course> getCoursesForStudent(Long studentId) {
        studentService.getStudentById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Active student not found"));
        return enrollmentRepository.findActiveEnrollmentsByStudentId(studentId, EnrollmentStatus.ENROLLED)
                .stream()
                .map(Enrollment::getCourse)
                .toList();
    }

    public List<Enrollment> getEnrollmentsByStatus(EnrollmentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Enrollment status is required");
        }
        return enrollmentRepository.findActiveEnrollmentsByStatus(status);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAllByIsActiveTrue();
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return enrollmentRepository.findByIdAndIsActiveTrue(id);
    }

    public Optional<Enrollment> updateEnrollment(Long id, Enrollment enrollment) {
        if (id == null || id <= 0 || enrollment == null) {
            return Optional.empty();
        }
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByIdAndIsActiveTrue(id);
        if (existingEnrollment.isEmpty()) {
            return Optional.empty();
        }

        Enrollment enrollmentToUpdate = existingEnrollment.get();
        if (enrollment.getStudent() != null) {
            if (enrollment.getStudent().getId() <= 0) {
                throw new IllegalArgumentException("Valid student ID is required");
            }
            Student student = studentService.getStudentById(enrollment.getStudent().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Active student not found"));
            enrollmentToUpdate.setStudent(student);
        }
        if (enrollment.getCourse() != null) {
            if (enrollment.getCourse().getId() <= 0) {
                throw new IllegalArgumentException("Valid course ID is required");
            }
            Course course = courseService.getCourseById(enrollment.getCourse().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Active course not found"));
            enrollmentToUpdate.setCourse(course);
        }
        if (enrollment.getEnrollmentDate() != null) {
            enrollmentToUpdate.setEnrollmentDate(enrollment.getEnrollmentDate());
        }
        if (enrollment.getStatus() != null) {
            enrollmentToUpdate.setStatus(enrollment.getStatus());
        }
        enrollmentToUpdate.setUpdatedDate(new Date());
        return Optional.of(enrollmentRepository.save(enrollmentToUpdate));
    }

    public boolean softDeleteEnrollment(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByIdAndIsActiveTrue(id);
        if (existingEnrollment.isEmpty()) {
            return false;
        }
        Enrollment enrollment = existingEnrollment.get();
        enrollment.setActive(false);
        enrollment.setUpdatedDate(new Date());
        enrollmentRepository.save(enrollment);
        return true;
    }

    private void validateCourseCapacity(Course course) {
        Long departmentId = course.getProgram().getDepartment().getId();
        Integer capacity = classroomRepository.findMaxActiveCapacityByDepartmentId(departmentId);
        if (capacity == null || capacity <= 0) {
            throw new IllegalArgumentException("No active classroom capacity is available for this course department");
        }
        long enrolled = enrollmentRepository.countActiveEnrollmentsByCourseId(course.getId(), EnrollmentStatus.ENROLLED);
        if (enrolled >= capacity) {
            throw new IllegalArgumentException("Course is full");
        }
    }
}
