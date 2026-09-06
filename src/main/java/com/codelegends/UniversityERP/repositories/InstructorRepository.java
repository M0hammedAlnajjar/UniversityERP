package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {
    List<Instructor> findAllByIsActiveTrue();

    Optional<Instructor> findByIdAndIsActiveTrue(Long id);
}
