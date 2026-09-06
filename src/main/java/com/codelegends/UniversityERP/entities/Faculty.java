package com.codelegends.UniversityERP.entities;

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
public class Faculty extends BaseClass {
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 255)
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @OneToMany(mappedBy = "faculty")
    private List<Department> departments = new ArrayList<>();
}
