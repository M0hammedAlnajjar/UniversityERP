package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findAllByIsActiveTrue();
    Optional<Program> findByIdAndIsActiveTrue(Long id);
}
