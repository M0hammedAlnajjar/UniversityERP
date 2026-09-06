package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByIsActiveTrue();

    Optional<Course> findByIdAndIsActiveTrue(Long id);

    boolean existsByCourseCode(String courseCode);

    boolean existsByCourseCodeAndIdNot(String courseCode, Long id);

    @Query("select c from Course c where c.program.id = :programId and c.isActive = true")
    List<Course> findActiveCoursesByProgramId(@Param("programId") Long programId);

    @Query("select c from Course c where c.instructor.id = :instructorId and c.isActive = true")
    List<Course> findActiveCoursesByInstructorId(@Param("instructorId") Long instructorId);

    @Query("select c from Course c where c.instructor is null and c.isActive = true")
    List<Course> findActiveCoursesWithoutInstructor();

    @Query("select count(c) from Course c where c.instructor.id = :instructorId and c.isActive = true")
    long countActiveCoursesByInstructorId(@Param("instructorId") Long instructorId);

    @Query("select c from Course c where c.isActive = true and not exists (select e.id from Enrollment e where e.course = c and e.isActive = true)")
    List<Course> findActiveCoursesWithNoEnrollments();

    @Query("select c.instructor.id, c.instructor.name, sum(c.creditHours) from Course c where c.instructor is not null and c.isActive = true and c.instructor.isActive = true group by c.instructor.id, c.instructor.name order by sum(c.creditHours) desc")
    List<Object[]> findInstructorCreditHoursRanking();
}
