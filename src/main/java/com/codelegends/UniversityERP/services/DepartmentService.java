package com.codelegends.UniversityERP.services;

import org.springframework.stereotype.Service;
import com.codelegends.UniversityERP.repositories.DepartmentRepository;
import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.entities.Faculty;
import java.util.List;
import java.util.Date;
import java.util.Optional;
@Service

public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final FacultyService facultyService;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            FacultyService facultyService
    ) {
        this.departmentRepository = departmentRepository;
        this.facultyService = facultyService;
    }

    public Department createDepartment(Department department) {

        if (department == null) {
            throw new IllegalArgumentException(
                    "Department cannot be null"
            );
        }

        if (department.getFaculty() == null
                || department.getFaculty().getId() <= 0) {

            throw new IllegalArgumentException(
                    "Faculty ID is required"
            );
        }

        Faculty faculty = facultyService
                .getFacultyById(
                        department.getFaculty().getId()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active faculty not found"
                        )
                );

        department.setFaculty(faculty);
        department.setActive(true);
        department.setCreatedDate(new Date());

        return departmentRepository.save(department);
    }
    public List<Department> getAllDepartments() {

        return departmentRepository.findAllByIsActiveTrue();
    }
    public Optional<Department> getDepartmentById(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        return departmentRepository.findByIdAndIsActiveTrue(id);
    }
}
