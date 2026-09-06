package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByIsActiveTrue();
    Optional<Student> findByIdAndIsActiveTrue(Long id);
}
