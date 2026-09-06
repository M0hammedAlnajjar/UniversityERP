package com.codelegends.UniversityERP.services;


import com.codelegends.UniversityERP.repositories.InstructorRepository;
import org.springframework.stereotype.Service;

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
}
