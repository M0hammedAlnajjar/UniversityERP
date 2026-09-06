package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.entities.Exam;
import com.codelegends.UniversityERP.repositories.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public Exam createExam(Exam exam) {

        if (exam == null) {
            throw new IllegalArgumentException(
                    "Exam cannot be null"
            );
        }

        if (exam.getCourse() == null
                || exam.getCourse().getId() <= 0) {

            throw new IllegalArgumentException(
                    "Course ID is required"
            );
        }

        Course course = courseService
                .getCourseById(
                        exam.getCourse().getId()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active course not found"
                        )
                );

        exam.setCourse(course);
        exam.setActive(true);
        exam.setCreatedDate(new Date());

        return examRepository.save(exam);
    }
}
