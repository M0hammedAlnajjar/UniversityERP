package com.codelegends.UniversityERP.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Exam extends BaseClass {
    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate examDate;

    @Column(nullable = false)
    private Double totalMarks;
}
