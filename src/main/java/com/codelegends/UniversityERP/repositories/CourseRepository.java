package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIsActiveTrue();
    Optional<Course> findByIdAndIsActiveTrue(Long id);
}
