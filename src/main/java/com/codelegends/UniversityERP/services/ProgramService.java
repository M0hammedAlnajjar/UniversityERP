package com.codelegends.UniversityERP.services;


import com.codelegends.UniversityERP.repositories.ProgramRepository;
import org.springframework.stereotype.Service;

@Service

public class ProgramService {
    private final ProgramRepository programRepository;
    private final DepartmentService departmentService;

    public ProgramService(
            ProgramRepository programRepository,
            DepartmentService departmentService
    ) {
        this.programRepository = programRepository;
        this.departmentService = departmentService;
    }
}
