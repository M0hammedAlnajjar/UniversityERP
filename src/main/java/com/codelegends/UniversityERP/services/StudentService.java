package com.codelegends.UniversityERP.services;
import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.entities.Student;
import org.springframework.stereotype.Service;

import java.util.Date;
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
}
