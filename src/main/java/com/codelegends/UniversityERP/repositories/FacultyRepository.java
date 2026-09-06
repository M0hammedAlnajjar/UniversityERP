package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByIsActiveTrue();
    Optional<Faculty> findByIdAndIsActiveTrue(Long id);
}
