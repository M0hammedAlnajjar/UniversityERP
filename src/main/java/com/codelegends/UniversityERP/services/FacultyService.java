package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(
            FacultyRepository facultyRepository
    ) {
        this.facultyRepository = facultyRepository;
    }
}
