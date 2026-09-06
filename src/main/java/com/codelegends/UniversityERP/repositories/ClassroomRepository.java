package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom,Long> {
    List<Classroom> findAllByIsActiveTrue();
    Optional<Classroom> findByIdAndIsActiveTrue(Long id);
}
