package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.GradeRepository;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final EnrollmentService enrollmentService;
    private final ExamService examService;

    public GradeService(
            GradeRepository gradeRepository,
            EnrollmentService enrollmentService,
            ExamService examService
    ) {
        this.gradeRepository = gradeRepository;
        this.enrollmentService = enrollmentService;
        this.examService = examService;
    }
}
