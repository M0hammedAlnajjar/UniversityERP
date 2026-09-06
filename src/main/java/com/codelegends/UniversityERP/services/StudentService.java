package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.entities.Student;
import com.codelegends.UniversityERP.enums.EnrollmentStatus;
import com.codelegends.UniversityERP.exceptions.ResourceNotFoundException;
import com.codelegends.UniversityERP.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ProgramService programService;

    public StudentService(StudentRepository studentRepository, ProgramService programService) {
        this.studentRepository = studentRepository;
        this.programService = programService;
    }

    public Student createStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (student.getProgram() == null || student.getProgram().getId() <= 0) {
            throw new IllegalArgumentException("Program ID is required");
        }
        Program program = programService.getProgramById(student.getProgram().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Active program not found"));
        student.setProgram(program);
        student.setActive(true);
        student.setCreatedDate(new Date());
        return studentRepository.save(student);
    }

    public List<Student> getStudentsByCourse(Long courseId) {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Valid course ID is required");
        }
        return studentRepository.findActiveStudentsByCourseId(courseId, EnrollmentStatus.ENROLLED);
    }

    public long countStudentsByUniversity(Long universityId) {
        if (universityId == null || universityId <= 0) {
            throw new IllegalArgumentException("Valid university ID is required");
        }
        return studentRepository.countActiveStudentsByUniversityId(universityId);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllByIsActiveTrue();
    }

    public Optional<Student> getStudentById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return studentRepository.findByIdAndIsActiveTrue(id);
    }

    public Optional<Student> updateStudent(Long id, Student student) {
        if (id == null || id <= 0 || student == null) {
            return Optional.empty();
        }
        Optional<Student> existingStudent = studentRepository.findByIdAndIsActiveTrue(id);
        if (existingStudent.isEmpty()) {
            return Optional.empty();
        }

        Student studentToUpdate = existingStudent.get();
        if (student.getProgram() != null) {
            if (student.getProgram().getId() <= 0) {
                throw new IllegalArgumentException("Valid program ID is required");
            }
            Program program = programService.getProgramById(student.getProgram().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Active program not found"));
            studentToUpdate.setProgram(program);
        }
        studentToUpdate.setName(student.getName());
        studentToUpdate.setGender(student.getGender());
        studentToUpdate.setPhoneNumber(student.getPhoneNumber());
        studentToUpdate.setMajor(student.getMajor());
        studentToUpdate.setUpdatedDate(new Date());
        return Optional.of(studentRepository.save(studentToUpdate));
    }

    public boolean softDeleteStudent(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        Optional<Student> existingStudent = studentRepository.findByIdAndIsActiveTrue(id);
        if (existingStudent.isEmpty()) {
            return false;
        }
        Student student = existingStudent.get();
        student.setActive(false);
        student.setUpdatedDate(new Date());
        studentRepository.save(student);
        return true;
    }
}
