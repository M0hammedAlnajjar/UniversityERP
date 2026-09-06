package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByIsActiveTrue();
    Optional<Grade> findByIdAndIsActiveTrue(Long id);

    boolean existsByEnrollment_IdAndExam_IdAndIsActiveTrue(Long enrollmentId, Long examId);

    @Query("select g from Grade g where g.enrollment.student.id = :studentId and g.isActive = true")
    List<Grade> findActiveGradesByStudentId(@Param("studentId") Long studentId);

    @Query("select g from Grade g where g.enrollment.student.program.id = :programId and g.isActive = true")
    List<Grade> findActiveGradesByProgramId(@Param("programId") Long programId);

    @Query("select avg(g.score) from Grade g where g.exam.course.id = :courseId and g.isActive = true")
    Double findAverageScoreByCourseId(@Param("courseId") Long courseId);

    @Query("select avg(g.score) from Grade g where g.enrollment.student.id = :studentId and g.isActive = true")
    Double findAverageScoreByStudentId(@Param("studentId") Long studentId);

    @Query("select avg(g.score) from Grade g where g.enrollment.student.program.id = :programId and g.isActive = true")
    Double findAverageScoreByProgramId(@Param("programId") Long programId);

    @Query("select g.enrollment.student.id, g.enrollment.student.name, avg(g.score) from Grade g where g.enrollment.student.program.id = :programId and g.isActive = true group by g.enrollment.student.id, g.enrollment.student.name order by avg(g.score) desc")
    List<Object[]> findStudentAverageRankingByProgramId(@Param("programId") Long programId);
}
