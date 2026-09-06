package com.codelegends.UniversityERP.entities;

import com.codelegends.UniversityERP.enums.DegreeLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Program extends BaseClass{

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private DegreeLevel degreeLevel;

    @Column(nullable = false)
    private Integer durationYears;
}
