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
public class Faculty extends BaseClass {
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 255)
    private String description;
}
