package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.GuardianRepository;
import org.springframework.stereotype.Service;

@Service
public class GuardianService {

    private final GuardianRepository guardianRepository;
    private final StudentService studentService;

    public GuardianService(
            GuardianRepository guardianRepository,
            StudentService studentService
    ) {
        this.guardianRepository = guardianRepository;
        this.studentService = studentService;
    }
}
