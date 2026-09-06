package com.codelegends.UniversityERP.services;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.entities.Student;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service

public class StudentService {
    public Student createStudent(Student student) {

        if (student == null) {
            throw new IllegalArgumentException(
                    "Student cannot be null"
            );
        }

        if (student.getProgram() == null
                || student.getProgram().getId() <= 0) {

            throw new IllegalArgumentException(
                    "Program ID is required"
            );
        }

        Program program = programService
                .getProgramById(
                        student.getProgram().getId()
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active program not found"
                        )
                );

        student.setProgram(program);
        student.setActive(true);
        student.setCreatedDate(new Date());

        return studentRepository.save(student);
    }
    public List<Student> getAllStudents() {

        return studentRepository.findAllByIsActiveTrue();
    }
    public Optional<Student> getStudentById(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        return studentRepository.findByIdAndIsActiveTrue(id);
    }
    public Optional<Student> updateStudent(
            Long id,
            Student student
    ) {

        if (id == null || student == null) {
            return Optional.empty();
        }

        Optional<Student> existingStudent =
                studentRepository.findByIdAndIsActiveTrue(id);

        if (existingStudent.isEmpty()) {
            return Optional.empty();
        }

        Student studentToUpdate = existingStudent.get();

        if (student.getProgram() != null) {

            if (student.getProgram().getId() <= 0) {
                throw new IllegalArgumentException(
                        "Valid program ID is required"
                );
            }

            Program program = programService
                    .getProgramById(student.getProgram().getId())
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Active program not found"
                            )
                    );

            studentToUpdate.setProgram(program);
        }

        studentToUpdate.setName(student.getName());
        studentToUpdate.setGender(student.getGender());
        studentToUpdate.setPhoneNumber(student.getPhoneNumber());
        studentToUpdate.setMajor(student.getMajor());
        studentToUpdate.setUpdatedDate(new Date());

        Student updatedStudent =
                studentRepository.save(studentToUpdate);

        return Optional.of(updatedStudent);
    }
}
