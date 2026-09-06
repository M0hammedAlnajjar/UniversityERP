package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByIsActiveTrue();
    Optional<Department> findByIdAndIsActiveTrue(Long id);

    @Query("select count(d) from Department d where d.faculty.university.id = :universityId and d.isActive = true")
    long countActiveDepartmentsByUniversityId(@Param("universityId") Long universityId);
}
