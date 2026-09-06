package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.UniversityRepository;
import org.springframework.stereotype.Service;
import com.codelegends.UniversityERP.entities.University;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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

    public Optional<University> getUniversityById(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        return universityRepository.findByIdAndIsActiveTrue(id);
    }
    public Optional<University> updateUniversity(
            Long id,
            University university
    ) {

        if (id == null || university == null) {
            return Optional.empty();
        }

        Optional<University> existingUniversity =
                universityRepository.findByIdAndIsActiveTrue(id);

        if (existingUniversity.isEmpty()) {
            return Optional.empty();
        }

        University universityToUpdate =
                existingUniversity.get();

        universityToUpdate.setName(university.getName());
        universityToUpdate.setLocation(university.getLocation());
        universityToUpdate.setUpdatedDate(new Date());

        University updatedUniversity =
                universityRepository.save(universityToUpdate);

        return Optional.of(updatedUniversity);
    }
    public boolean softDeleteUniversity(Long id) {

        if (id == null) {
            return false;
        }

        Optional<University> existingUniversity =
                universityRepository.findByIdAndIsActiveTrue(id);

        if (existingUniversity.isEmpty()) {
            return false;
        }

        University university =
                existingUniversity.get();

        university.setActive(false);
        university.setUpdatedDate(new Date());

        universityRepository.save(university);

        return true;
    }

}
