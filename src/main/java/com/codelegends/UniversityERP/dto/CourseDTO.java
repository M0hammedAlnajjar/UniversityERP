package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course title is required")
    @Size(max = 150, message = "Course title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Course code is required")
    @Size(max = 20, message = "Course code must not exceed 20 characters")
    private String courseCode;

    @NotNull(message = "Credit hours are required")
    @Positive(message = "Credit hours must be positive")
    private Integer creditHours;

    @NotNull(message = "Program ID is required")
    @Positive(message = "Program ID must be positive")
    private Long programId;

    private String programName;

    @Positive(message = "Instructor ID must be positive")
    private Long instructorId;

    private String instructorName;

    public static CourseDTO convertToDTO(Course entity) {
        if (entity == null) {
            return null;
        }
        return CourseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .courseCode(entity.getCourseCode())
                .creditHours(entity.getCreditHours())
                .programId(entity.getProgram() == null ? null : entity.getProgram().getId())
                .programName(entity.getProgram() == null ? null : entity.getProgram().getName())
                .instructorId(entity.getInstructor() == null ? null : entity.getInstructor().getId())
                .instructorName(entity.getInstructor() == null ? null : entity.getInstructor().getName())
                .build();
    }

    public static List<CourseDTO> convertToDTO(List<Course> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(CourseDTO::convertToDTO).toList();
    }
}
