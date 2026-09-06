package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.codelegends.UniversityERP.enums.EnrollmentStatus;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findAllByIsActiveTrue();
    Optional<Enrollment> findByIdAndIsActiveTrue(Long id);
    boolean existsByStudent_IdAndCourse_IdAndStatusAndIsActiveTrue(
            Long studentId,
            Long courseId,
            EnrollmentStatus status
    );
}
