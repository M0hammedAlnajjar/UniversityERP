package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByIsActiveTrue();
    Optional<Student> findByIdAndIsActiveTrue(Long id);

    @Query("select distinct s from Student s join s.enrollments e where e.course.id = :courseId and e.status = :status and e.isActive = true and s.isActive = true")
    List<Student> findActiveStudentsByCourseId(
            @Param("courseId") Long courseId,
            @Param("status") EnrollmentStatus status
    );

    @Query("select count(s) from Student s where s.program.department.faculty.university.id = :universityId and s.isActive = true")
    long countActiveStudentsByUniversityId(@Param("universityId") Long universityId);
}
