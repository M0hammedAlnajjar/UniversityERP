package com.codelegends.UniversityERP.dto;

import com.codelegends.UniversityERP.entities.University;
import jakarta.validation.constraints.NotBlank;
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
public class UniversityDTO {

    private Long id;

    @NotBlank(message = "University name is required")
    @Size(max = 100, message = "University name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "University location is required")
    @Size(max = 150, message = "University location must not exceed 150 characters")
    private String location;

    public static UniversityDTO convertToDTO(University entity) {
        if (entity == null) {
            return null;
        }
        return UniversityDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .build();
    }

    public static List<UniversityDTO> convertToDTO(List<University> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(UniversityDTO::convertToDTO).toList();
    }
}
