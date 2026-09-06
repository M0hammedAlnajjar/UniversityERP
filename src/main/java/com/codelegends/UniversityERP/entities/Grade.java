package com.codelegends.UniversityERP.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Grade extends BaseClass {
    @Column(nullable = false)
    private Double score;

    @Column(length = 2, nullable = false)
    private String letterGrade;
}

