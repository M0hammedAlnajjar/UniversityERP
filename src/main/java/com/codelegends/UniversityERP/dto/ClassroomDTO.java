package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.Classroom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
public class ClassroomDTO {
    private Long id;

    @NotBlank(message = "Room number is required")
    @Size(max = 20, message = "Room number must not exceed 20 characters")
    private String roomNumber;

    @NotNull(message = "Floor is required")
    @PositiveOrZero(message = "Floor must be zero or greater")
    private Integer floor;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Integer capacity;

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be positive")
    private Long departmentId;

    private String departmentName;

    public static ClassroomDTO convertToDTO(Classroom entity) {
        if (entity == null) return null;
        return ClassroomDTO.builder()
                .id(entity.getId())
                .roomNumber(entity.getRoomNumber())
                .floor(entity.getFloor())
                .capacity(entity.getCapacity())
                .departmentId(entity.getDepartment() == null ? null : entity.getDepartment().getId())
                .departmentName(entity.getDepartment() == null ? null : entity.getDepartment().getName())
                .build();
    }

    public static List<ClassroomDTO> convertToDTO(List<Classroom> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(ClassroomDTO::convertToDTO).toList();
    }
}
