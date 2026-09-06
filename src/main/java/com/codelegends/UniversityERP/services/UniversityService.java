package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.UniversityRepository;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;

    }
}
