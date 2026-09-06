package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.ExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final CourseService courseService;

    public ExamService(
            ExamRepository examRepository,
            CourseService courseService
    ) {
        this.examRepository = examRepository;
        this.courseService = courseService;
    }
}
