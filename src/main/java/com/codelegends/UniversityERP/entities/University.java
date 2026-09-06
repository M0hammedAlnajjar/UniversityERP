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
public class University extends BaseClass {

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 150)
    private String  location;

}
