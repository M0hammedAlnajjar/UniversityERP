package com.codelegends.UniversityERP.services;


import com.codelegends.UniversityERP.repositories.CourseRepository;
import org.springframework.stereotype.Service;

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

}
