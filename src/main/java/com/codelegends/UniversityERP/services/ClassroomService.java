package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.repositories.ClassroomRepository;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final DepartmentService departmentService;

    public ClassroomService(
            ClassroomRepository classroomRepository,
            DepartmentService departmentService
    ) {
        this.classroomRepository = classroomRepository;
        this.departmentService = departmentService;
    }
}
