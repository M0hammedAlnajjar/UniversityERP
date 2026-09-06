package com.codelegends.UniversityERP.services;

import com.codelegends.UniversityERP.entities.Classroom;
import com.codelegends.UniversityERP.entities.Department;
import com.codelegends.UniversityERP.repositories.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Classroom createClassroom(Classroom classroom) {

        if (classroom == null) {
            throw new IllegalArgumentException("Classroom cannot be null");
        }

        if (classroom.getDepartment() == null
                || classroom.getDepartment().getId() <= 0) {
            throw new IllegalArgumentException("Department ID is required");
        }

        Department department = departmentService
                .getDepartmentById(classroom.getDepartment().getId())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Active department not found"
                        )
                );

        classroom.setDepartment(department);
        classroom.setActive(true);
        classroom.setCreatedDate(new Date());

        return classroomRepository.save(classroom);
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAllByIsActiveTrue();
    }

    public Optional<Classroom> getClassroomById(Long id) {

        if (id == null || id <= 0) {
            return Optional.empty();
        }

        return classroomRepository.findByIdAndIsActiveTrue(id);
    }

    public Optional<Classroom> updateClassroom(
            Long id,
            Classroom classroom
    ) {

        if (id == null || id <= 0 || classroom == null) {
            return Optional.empty();
        }

        Optional<Classroom> existingClassroom =
                classroomRepository.findByIdAndIsActiveTrue(id);

        if (existingClassroom.isEmpty()) {
            return Optional.empty();
        }

        Classroom classroomToUpdate = existingClassroom.get();

        classroomToUpdate.setRoomNumber(classroom.getRoomNumber());
        classroomToUpdate.setFloor(classroom.getFloor());
        classroomToUpdate.setCapacity(classroom.getCapacity());

        if (classroom.getDepartment() != null) {
            if (classroom.getDepartment().getId() <= 0) {
                throw new IllegalArgumentException(
                        "Valid department ID is required"
                );
            }

            Department department = departmentService
                    .getDepartmentById(classroom.getDepartment().getId())
                    .orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Active department not found"
                            )
                    );

            classroomToUpdate.setDepartment(department);
        }

        classroomToUpdate.setUpdatedDate(new Date());

        Classroom updatedClassroom =
                classroomRepository.save(classroomToUpdate);

        return Optional.of(updatedClassroom);
    }

    public boolean softDeleteClassroom(Long id) {

        if (id == null || id <= 0) {
            return false;
        }

        Optional<Classroom> existingClassroom =
                classroomRepository.findByIdAndIsActiveTrue(id);

        if (existingClassroom.isEmpty()) {
            return false;
        }

        Classroom classroom = existingClassroom.get();
        classroom.setActive(false);
        classroom.setUpdatedDate(new Date());

        classroomRepository.save(classroom);

        return true;
    }
}
