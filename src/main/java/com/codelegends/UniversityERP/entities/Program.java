package com.codelegends.UniversityERP.entities;

import com.codelegends.UniversityERP.enums.DegreeLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "program")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "program")
    private List<Course> courses = new ArrayList<>();
}
