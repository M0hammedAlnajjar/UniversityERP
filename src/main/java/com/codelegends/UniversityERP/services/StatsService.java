package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.dto.InstructorStatsDTO;
import com.codelegends.UniversityERP.dto.ProgramStatsDTO;
import com.codelegends.UniversityERP.dto.UniversityStatsDTO;
import com.codelegends.UniversityERP.entities.Instructor;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.entities.University;
import com.codelegends.UniversityERP.enums.EnrollmentStatus;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.repositories.DepartmentRepository;
import com.codelegends.UniversityERP.repositories.EnrollmentRepository;
import com.codelegends.UniversityERP.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final UniversityService universityService;
    private final InstructorService instructorService;
    private final ProgramService programService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final GradeService gradeService;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StatsService(
            UniversityService universityService,
            InstructorService instructorService,
            ProgramService programService,
            StudentService studentService,
            CourseService courseService,
            GradeService gradeService,
            FacultyRepository facultyRepository,
            DepartmentRepository departmentRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        this.universityService = universityService;
        this.instructorService = instructorService;
        this.programService = programService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public UniversityStatsDTO getUniversityStats(Long universityId) {
        University university = universityService.getUniversityById(universityId)
                .orElseThrow(() -> new ResourceNotFoundException("Active university not found"));
        return UniversityStatsDTO.builder()
                .universityId(university.getId())
                .universityName(university.getName())
                .activeFaculties(facultyRepository.countActiveFacultiesByUniversityId(universityId))
                .activeDepartments(departmentRepository.countActiveDepartmentsByUniversityId(universityId))
                .activeStudents(studentService.countStudentsByUniversity(universityId))
                .build();
    }

    public InstructorStatsDTO getInstructorStats(Long instructorId) {
        Instructor instructor = instructorService.getInstructorById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Active instructor not found"));
        return InstructorStatsDTO.builder()
                .instructorId(instructor.getId())
                .instructorName(instructor.getName())
                .totalCoursesTaught(courseService.countCoursesByInstructor(instructorId))
                .build();
    }

    public ProgramStatsDTO getProgramStats(Long programId) {
        Program program = programService.getProgramById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Active program not found"));
        return ProgramStatsDTO.builder()
                .programId(program.getId())
                .programName(program.getName())
                .totalEnrolledStudents(enrollmentRepository.countDistinctActiveStudentsByProgramId(
                        programId,
                        EnrollmentStatus.ENROLLED
                ))
                .averageScore(gradeService.getProgramAverageScore(programId))
                .averageGpa(gradeService.getProgramAverageGpa(programId))
                .build();
    }
}
