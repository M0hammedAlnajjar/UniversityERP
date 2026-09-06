package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Instructor;
import com.codelegends.UniversityERP.entities.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIsActiveTrue();
    Optional<Course> findByIdAndIsActiveTrue(Long id);
    boolean existsByCourseCode(String courseCode);
    boolean existsByCourseCodeAndIdNot(
            String courseCode,
            Long id
    );

    public Optional<Course> updateCourse(
            Long id,
            Course course
    ) {

        if (id == null || course == null) {
            return Optional.empty();
        }

        Optional<Course> existingCourse =
                courseRepository.findByIdAndIsActiveTrue(id);

        if (existingCourse.isEmpty()) {
            return Optional.empty();
        }

        if (courseRepository.existsByCourseCodeAndIdNot(
                course.getCourseCode(),
                id
        )) {
            throw new IllegalArgumentException(
                    "Course code already exists"
            );
        }

        Course courseToUpdate = existingCourse.get();

        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setCourseCode(course.getCourseCode());
        courseToUpdate.setCreditHours(
                course.getCreditHours()
        );

        if (course.getProgram() != null
                && course.getProgram().getId() > 0) {

            Program program = programService
                    .getProgramById(
                            course.getProgram().getId()
                    )
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Active program not found"
                            )
                    );

            courseToUpdate.setProgram(program);
        }

        if (course.getInstructor() != null
                && course.getInstructor().getId() > 0) {

            Instructor instructor = instructorService
                    .getInstructorById(
                            course.getInstructor().getId()
                    )
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Active instructor not found"
                            )
                    );

            courseToUpdate.setInstructor(instructor);
        }

        courseToUpdate.setUpdatedDate(new Date());

        Course updatedCourse =
                courseRepository.save(courseToUpdate);

        return Optional.of(updatedCourse);
    }
}
