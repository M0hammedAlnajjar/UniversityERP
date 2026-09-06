package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.FacultyRepository;
import org.springframework.stereotype.Service;
import com.codelegends.UniversityERP.entities.Faculty;
import com.codelegends.UniversityERP.entities.University;
import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final UniversityService universityService;
    public FacultyService(
            FacultyRepository facultyRepository,
            UniversityService universityService
    ) {
        this.facultyRepository = facultyRepository;
        this.universityService = universityService;
    }
    public Faculty createFaculty(Faculty faculty) {

        if (faculty == null) {
            throw new IllegalArgumentException(
                    "Faculty cannot be null"
            );
        }

        if (faculty.getUniversity() == null
                || faculty.getUniversity().getId() <= 0) {

            throw new IllegalArgumentException(
                    "University ID is required"
            );
        }

        University university = universityService
                .getUniversityById(
                        faculty.getUniversity().getId()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active university not found"
                        )
                );

        faculty.setUniversity(university);
        faculty.setActive(true);
        faculty.setCreatedDate(new Date());

        return facultyRepository.save(faculty);
    }
    public List<Faculty> getAllFaculties() {

        return facultyRepository.findAllByIsActiveTrue();
    }
    public Optional<Faculty> getFacultyById(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        return facultyRepository.findByIdAndIsActiveTrue(id);
    }
    public Optional<Faculty> updateFaculty(
            Long id,
            Faculty faculty
    ) {

        if (id == null || faculty == null) {
            return Optional.empty();
        }

        Optional<Faculty> existingFaculty =
                facultyRepository.findByIdAndIsActiveTrue(id);

        if (existingFaculty.isEmpty()) {
            return Optional.empty();
        }

        Faculty facultyToUpdate = existingFaculty.get();

        facultyToUpdate.setName(faculty.getName());
        facultyToUpdate.setDescription(faculty.getDescription());

        if (faculty.getUniversity() != null
                && faculty.getUniversity().getId() > 0) {

            University university = universityService
                    .getUniversityById(
                            faculty.getUniversity().getId()
                    )
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Active university not found"
                            )
                    );

            facultyToUpdate.setUniversity(university);
        }

        facultyToUpdate.setUpdatedDate(new Date());

        Faculty updatedFaculty =
                facultyRepository.save(facultyToUpdate);

        return Optional.of(updatedFaculty);
    }

}
