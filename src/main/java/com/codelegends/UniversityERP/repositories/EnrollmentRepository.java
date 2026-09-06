package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Enrollment;
import com.codelegends.UniversityERP.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByIsActiveTrue();

    Optional<Enrollment> findByIdAndIsActiveTrue(Long id);

    boolean existsByStudent_IdAndCourse_IdAndStatusAndIsActiveTrue(
            Long studentId,
            Long courseId,
            EnrollmentStatus status
    );

    boolean existsByStudent_IdAndCourse_IdAndIsActiveTrue(Long studentId, Long courseId);

    @Query("select e from Enrollment e where e.status = :status and e.isActive = true")
    List<Enrollment> findActiveEnrollmentsByStatus(@Param("status") EnrollmentStatus status);

    @Query("select e from Enrollment e where e.student.id = :studentId and e.status = com.codelegends.UniversityERP.enums.EnrollmentStatus.ENROLLED and e.isActive = true")
    List<Enrollment> findActiveEnrollmentsByStudentId(@Param("studentId") Long studentId);

    @Query("select count(e) from Enrollment e where e.course.id = :courseId and e.status = com.codelegends.UniversityERP.enums.EnrollmentStatus.ENROLLED and e.isActive = true")
    long countActiveEnrolledByCourseId(@Param("courseId") Long courseId);

    @Query("select count(distinct e.student.id) from Enrollment e where e.course.program.id = :programId and e.status = com.codelegends.UniversityERP.enums.EnrollmentStatus.ENROLLED and e.isActive = true")
    long countDistinctActiveStudentsByProgramId(@Param("programId") Long programId);
}
