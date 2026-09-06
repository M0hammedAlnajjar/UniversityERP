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
public class Guardian extends BaseClass{
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String relationship;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "student")
    private List<Guardian> guardians = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
