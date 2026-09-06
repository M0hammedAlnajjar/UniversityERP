package com.codelegends.UniversityERP.services;

import org.springframework.stereotype.Service;
import com.codelegends.UniversityERP.repositories.DepartmentRepository;
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
}
