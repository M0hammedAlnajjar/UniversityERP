package com.codelegends.UniversityERP.services;


import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.repositories.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Optional<Program> getProgramById(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        return programRepository.findByIdAndIsActiveTrue(id);
    }
    public Optional<Program> updateProgram(
            Long id,
            Program program
    ) {

        if (id == null || program == null) {
            return Optional.empty();
        }

        Optional<Program> existingProgram =
                programRepository.findByIdAndIsActiveTrue(id);

        if (existingProgram.isEmpty()) {
            return Optional.empty();
        }

        Program programToUpdate = existingProgram.get();

        programToUpdate.setName(program.getName());
        programToUpdate.setDegreeLevel(
                program.getDegreeLevel()
        );
        programToUpdate.setDurationYears(
                program.getDurationYears()
        );

        if (program.getDepartment() != null
                && program.getDepartment().getId() > 0) {

            Department department = departmentService
                    .getDepartmentById(
                            program.getDepartment().getId()
                    )
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Active department not found"
                            )
                    );

            programToUpdate.setDepartment(department);
        }

        programToUpdate.setUpdatedDate(new Date());

        Program updatedProgram =
                programRepository.save(programToUpdate);

        return Optional.of(updatedProgram);
    }
    public boolean softDeleteProgram(Long id) {

        if (id == null) {
            return false;
        }

        Optional<Program> existingProgram =
                programRepository.findByIdAndIsActiveTrue(id);

        if (existingProgram.isEmpty()) {
            return false;
        }

        Program program = existingProgram.get();

        program.setActive(false);
        program.setUpdatedDate(new Date());

        programRepository.save(program);

        return true;
    }
}
