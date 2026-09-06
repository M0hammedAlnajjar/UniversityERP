package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {
    List<Grade> findAllByIsActiveTrue();
    Optional<Grade> findByIdAndIsActiveTrue(Long id);
}
