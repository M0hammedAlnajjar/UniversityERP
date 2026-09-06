package com.codelegends.UniversityERP.services;


import com.codelegends.UniversityERP.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Instructor;
import com.codelegends.UniversityERP.entities.Program;
import java.util.List;
import java.util.Date;
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final ProgramService programService;
    private final InstructorService instructorService;

    public CourseService(
            CourseRepository courseRepository,
            ProgramService programService,
            InstructorService instructorService
    ) {
        this.courseRepository = courseRepository;
        this.programService = programService;
        this.instructorService = instructorService;
    }

    public Course createCourse(Course course) {

        if (course == null) {
            throw new IllegalArgumentException(
                    "Course cannot be null"
            );
        }

        if (courseRepository.existsByCourseCode(
                course.getCourseCode()
        )) {
            throw new IllegalArgumentException(
                    "Course code already exists"
            );
        }

        if (course.getProgram() == null
                || course.getProgram().getId() <= 0) {

            throw new IllegalArgumentException(
                    "Program ID is required"
            );
        }

        Program program = programService
                .getProgramById(course.getProgram().getId())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active program not found"
                        )
                );

        course.setProgram(program);

        if (course.getInstructor() != null) {

            if (course.getInstructor().getId() <= 0) {
                throw new IllegalArgumentException(
                        "Valid instructor ID is required"
                );
            }

            Instructor instructor = instructorService
                    .getInstructorById(
                            course.getInstructor().getId()
                    )
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Active instructor not found"
                            )
                    );

            course.setInstructor(instructor);
        }

        course.setActive(true);
        course.setCreatedDate(new Date());

        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {

        return courseRepository.findAllByIsActiveTrue();
    }

}
