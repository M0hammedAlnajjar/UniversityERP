package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByIsActiveTrue();
    Optional<Exam> findByIdAndIsActiveTrue(Long id);

    @Query("select e from Exam e where e.course.id = :courseId and e.isActive = true order by e.examDate")
    List<Exam> findActiveExamsByCourseId(@Param("courseId") Long courseId);
}
