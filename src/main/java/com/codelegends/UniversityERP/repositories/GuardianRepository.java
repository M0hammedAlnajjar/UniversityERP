package com.codelegends.UniversityERP.repositories;

import com.codelegends.UniversityERP.entities.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian,Long> {
    List<Guardian> findAllByIsActiveTrue();
    Optional<Guardian> findByIdAndIsActiveTrue(Long id);
}
