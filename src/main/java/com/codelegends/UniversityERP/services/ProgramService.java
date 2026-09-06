package com.codelegends.UniversityERP.services;


import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.repositories.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public Program createProgram(Program program) {

        if (program == null) {
            throw new IllegalArgumentException(
                    "Program cannot be null"
            );
        }

        if (program.getDepartment() == null
                || program.getDepartment().getId() <= 0) {

            throw new IllegalArgumentException(
                    "Department ID is required"
            );
        }

        Department department = departmentService
                .getDepartmentById(
                        program.getDepartment().getId()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active department not found"
                        )
                );

        program.setDepartment(department);
        program.setActive(true);
        program.setCreatedDate(new Date());

        return programRepository.save(program);
    }
    public List<Program> getAllPrograms() {

        return programRepository.findAllByIsActiveTrue();
    }
}
