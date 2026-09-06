package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findAllByIsActiveTrue();
    Optional<Classroom> findByIdAndIsActiveTrue(Long id);

    @Query("select max(c.capacity) from Classroom c where c.department.id = :departmentId and c.isActive = true")
    Integer findMaxActiveCapacityByDepartmentId(@Param("departmentId") Long departmentId);
}
