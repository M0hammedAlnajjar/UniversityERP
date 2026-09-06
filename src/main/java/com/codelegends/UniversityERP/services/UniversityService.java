package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.UniversityRepository;
import org.springframework.stereotype.Service;
import com.codelegends.UniversityERP.entities.University;
import java.util.Date;
import java.util.List;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;

    }

    public University createUniversity(University university) {

        if (university == null) {
            throw new IllegalArgumentException(
                    "University cannot be null"
            );
        }

        university.setActive(true);
        university.setCreatedDate(new Date());

        return universityRepository.save(university);
    }
    public List<University> getAllUniversities() {

        return universityRepository.findAllByIsActiveTrue();
    }
}
