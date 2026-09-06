package com.codelegends.UniversityERP.services;


import com.codelegends.UniversityERP.repositories.InstructorRepository;
import org.springframework.stereotype.Service;
import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.entities.Instructor;
import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service

public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final DepartmentService departmentService;

    public InstructorService(
            InstructorRepository instructorRepository,
            DepartmentService departmentService
    ) {
        this.instructorRepository = instructorRepository;
        this.departmentService = departmentService;
    }
    public Instructor createInstructor(Instructor instructor) {

        if (instructor == null) {
            throw new IllegalArgumentException(
                    "Instructor cannot be null"
            );
        }

        if (instructorRepository.existsByEmail(
                instructor.getEmail()
        )) {
            throw new IllegalArgumentException(
                    "Instructor email already exists"
            );
        }

        if (instructor.getDepartment() == null
                || instructor.getDepartment().getId() <= 0) {

            throw new IllegalArgumentException(
                    "Department ID is required"
            );
        }

        Department department = departmentService
                .getDepartmentById(
                        instructor.getDepartment().getId()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active department not found"
                        )
                );

        instructor.setDepartment(department);
        instructor.setActive(true);
        instructor.setCreatedDate(new Date());

        return instructorRepository.save(instructor);
    }

    public List<Instructor> getAllInstructors() {

        return instructorRepository.findAllByIsActiveTrue();
    }

    public Optional<Instructor> getInstructorById(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        return instructorRepository.findByIdAndIsActiveTrue(id);
    }
}
