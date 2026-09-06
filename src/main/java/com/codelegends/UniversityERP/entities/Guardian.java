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
public class Guardian extends BaseClass{
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String relationship;

    @Column(length = 20, nullable = false)
    private String phoneNumber;
}
