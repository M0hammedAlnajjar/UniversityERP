package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.entities.Guardian;
import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.repositories.GuardianRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Guardian createGuardian(Guardian guardian) {

        if (guardian == null) {
            throw new IllegalArgumentException("Guardian cannot be null");
        }

        if (guardian.getStudent() == null
                || guardian.getStudent().getId() <= 0) {
            throw new IllegalArgumentException("Student ID is required");
        }

        Student student = studentService
                .getStudentById(guardian.getStudent().getId())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active student not found"
                        )
                );

        guardian.setStudent(student);
        guardian.setActive(true);
        guardian.setCreatedDate(new Date());

        return guardianRepository.save(guardian);
    }

    public List<Guardian> getAllGuardians() {
        return guardianRepository.findAllByIsActiveTrue();
    }

    public Optional<Guardian> getGuardianById(Long id) {

        if (id == null || id <= 0) {
            return Optional.empty();
        }

        return guardianRepository.findByIdAndIsActiveTrue(id);
    }

    public Optional<Guardian> updateGuardian(
            Long id,
            Guardian guardian
    ) {

        if (id == null || id <= 0 || guardian == null) {
            return Optional.empty();
        }

        Optional<Guardian> existingGuardian =
                guardianRepository.findByIdAndIsActiveTrue(id);

        if (existingGuardian.isEmpty()) {
            return Optional.empty();
        }

        Guardian guardianToUpdate = existingGuardian.get();

        guardianToUpdate.setName(guardian.getName());
        guardianToUpdate.setRelationship(guardian.getRelationship());
        guardianToUpdate.setPhoneNumber(guardian.getPhoneNumber());

        if (guardian.getStudent() != null) {
            if (guardian.getStudent().getId() <= 0) {
                throw new IllegalArgumentException(
                        "Valid student ID is required"
                );
            }

            Student student = studentService
                    .getStudentById(guardian.getStudent().getId())
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Active student not found"
                            )
                    );

            guardianToUpdate.setStudent(student);
        }

        guardianToUpdate.setUpdatedDate(new Date());

        Guardian updatedGuardian =
                guardianRepository.save(guardianToUpdate);

        return Optional.of(updatedGuardian);
    }

    public boolean softDeleteGuardian(Long id) {

        if (id == null || id <= 0) {
            return false;
        }

        Optional<Guardian> existingGuardian =
                guardianRepository.findByIdAndIsActiveTrue(id);

        if (existingGuardian.isEmpty()) {
            return false;
        }

        Guardian guardian = existingGuardian.get();
        guardian.setActive(false);
        guardian.setUpdatedDate(new Date());

        guardianRepository.save(guardian);

        return true;
    }
}
