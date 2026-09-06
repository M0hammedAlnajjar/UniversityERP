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
public class Course extends BaseClass {
    @Column(length = 150, nullable = false)
    private String title;

    @Column(length = 20, nullable = false, unique = true)
    private String courseCode;

    @Column(nullable = false)
    private Integer creditHours;



}
