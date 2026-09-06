package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Instructor;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new IllegalArgumentException("Course code already exists");
        }
        if (course.getProgram() == null || course.getProgram().getId() <= 0) {
            throw new IllegalArgumentException("Program ID is required");
        }

        Program program = programService.getProgramById(course.getProgram().getId())
                .orElseThrow(() -> new IllegalArgumentException("Active program not found"));
        course.setProgram(program);

        if (course.getInstructor() != null) {
            if (course.getInstructor().getId() <= 0) {
                throw new IllegalArgumentException("Valid instructor ID is required");
            }
            Instructor instructor = instructorService.getInstructorById(course.getInstructor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Active instructor not found"));
            course.setInstructor(instructor);
        }

        course.setActive(true);
        course.setCreatedDate(new Date());
        return courseRepository.save(course);
    }

    public Course assignInstructor(Long courseId, Long instructorId) {
        Course course = getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Active course not found"));
        Instructor instructor = instructorService.getInstructorById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Active instructor not found"));
        course.setInstructor(instructor);
        course.setUpdatedDate(new Date());
        return courseRepository.save(course);
    }

    public List<Course> getCoursesByInstructor(Long instructorId) {
        instructorService.getInstructorById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Active instructor not found"));
        return courseRepository.findActiveCoursesByInstructorId(instructorId);
    }

    public List<Course> getCoursesByProgram(Long programId) {
        programService.getProgramById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Active program not found"));
        return courseRepository.findActiveCoursesByProgramId(programId);
    }

    public List<Course> getCoursesWithoutInstructor() {
        return courseRepository.findActiveCoursesWithoutInstructor();
    }

    public List<Course> getCoursesWithNoEnrollments() {
        return courseRepository.findActiveCoursesWithNoEnrollments();
    }

    public long countCoursesByInstructor(Long instructorId) {
        instructorService.getInstructorById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Active instructor not found"));
        return courseRepository.countActiveCoursesByInstructorId(instructorId);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAllByIsActiveTrue();
    }

    public Optional<Course> getCourseById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return courseRepository.findByIdAndIsActiveTrue(id);
    }

    public Optional<Course> updateCourse(Long id, Course course) {
        if (id == null || id <= 0 || course == null) {
            return Optional.empty();
        }
        Optional<Course> existingCourse = courseRepository.findByIdAndIsActiveTrue(id);
        if (existingCourse.isEmpty()) {
            return Optional.empty();
        }
        if (courseRepository.existsByCourseCodeAndIdNot(course.getCourseCode(), id)) {
            throw new IllegalArgumentException("Course code already exists");
        }

        Course courseToUpdate = existingCourse.get();
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setCourseCode(course.getCourseCode());
        courseToUpdate.setCreditHours(course.getCreditHours());

        if (course.getProgram() != null && course.getProgram().getId() > 0) {
            Program program = programService.getProgramById(course.getProgram().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Active program not found"));
            courseToUpdate.setProgram(program);
        }
        if (course.getInstructor() != null && course.getInstructor().getId() > 0) {
            Instructor instructor = instructorService.getInstructorById(course.getInstructor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Active instructor not found"));
            courseToUpdate.setInstructor(instructor);
        }

        courseToUpdate.setUpdatedDate(new Date());
        return Optional.of(courseRepository.save(courseToUpdate));
    }

    public boolean softDeleteCourse(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        Optional<Course> existingCourse = courseRepository.findByIdAndIsActiveTrue(id);
        if (existingCourse.isEmpty()) {
            return false;
        }
        Course course = existingCourse.get();
        course.setActive(false);
        course.setUpdatedDate(new Date());
        courseRepository.save(course);
        return true;
    }
}
