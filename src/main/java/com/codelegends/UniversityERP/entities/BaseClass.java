package com.codelegends.UniversityERP.entities;



import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseClass {
@Id
@GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private  boolean isActive;
    private  Date  createdDate;
    private Date updatedDate;
}
