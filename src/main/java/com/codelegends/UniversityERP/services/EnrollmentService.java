package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;
import com.codelegends.UniversityERP.entities.Enrollment;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentService studentService,
            CourseService courseService
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public List<Enrollment> getAllEnrollments() {

        return enrollmentRepository.findAllByIsActiveTrue();
    }
    public Optional<Enrollment> getEnrollmentById(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        return enrollmentRepository.findByIdAndIsActiveTrue(id);
    }
}
