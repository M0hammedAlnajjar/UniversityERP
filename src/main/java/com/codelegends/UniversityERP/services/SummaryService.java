package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.dto.UniversitySummaryDTO;
import org.springframework.stereotype.Service;

@Service
public class SummaryService {

    private final UniversityService universityService;
    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final ProgramService programService;
    private final CourseService courseService;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final ExamService examService;
    private final GradeService gradeService;
    private final GuardianService guardianService;
    private final ClassroomService classroomService;

    public SummaryService(
            UniversityService universityService,
            FacultyService facultyService,
            DepartmentService departmentService,
            ProgramService programService,
            CourseService courseService,
            InstructorService instructorService,
            StudentService studentService,
            EnrollmentService enrollmentService,
            ExamService examService,
            GradeService gradeService,
            GuardianService guardianService,
            ClassroomService classroomService
    ) {
        this.universityService = universityService;
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.programService = programService;
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
        this.examService = examService;
        this.gradeService = gradeService;
        this.guardianService = guardianService;
        this.classroomService = classroomService;
    }

    public UniversitySummaryDTO getSummary() {
        return UniversitySummaryDTO.builder()
                .universities((long) universityService.getAllUniversities().size())
                .faculties((long) facultyService.getAllFaculties().size())
                .departments((long) departmentService.getAllDepartments().size())
                .programs((long) programService.getAllPrograms().size())
                .courses((long) courseService.getAllCourses().size())
                .instructors((long) instructorService.getAllInstructors().size())
                .students((long) studentService.getAllStudents().size())
                .enrollments((long) enrollmentService.getAllEnrollments().size())
                .exams((long) examService.getAllExams().size())
                .grades((long) gradeService.getAllGrades().size())
                .guardians((long) guardianService.getAllGuardians().size())
                .classrooms((long) classroomService.getAllClassrooms().size())
                .build();
    }
}
