package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Guardian;
import com.codelegends.UniversityERP.services.GuardianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/guardians")
public class GuardianController {
    private final GuardianService guardianService;
    public GuardianController(GuardianService guardianService) { this.guardianService = guardianService; }
    @PostMapping public ResponseEntity<Guardian> createGuardian(@RequestBody Guardian guardian) { return ResponseEntity.ok(guardianService.createGuardian(guardian)); }
    @GetMapping public ResponseEntity<List<Guardian>> getAllGuardians() { return ResponseEntity.ok(guardianService.getAllGuardians()); }
    @GetMapping("/{id}") public ResponseEntity<Guardian> getGuardianById(@PathVariable Long id) { return guardianService.getGuardianById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Guardian> updateGuardian(@PathVariable Long id, @RequestBody Guardian guardian) { return guardianService.updateGuardian(id, guardian).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteGuardian(@PathVariable Long id) { return guardianService.softDeleteGuardian(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
