package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Program;
import com.codelegends.UniversityERP.services.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {
    private final ProgramService programService;
    public ProgramController(ProgramService programService) { this.programService = programService; }
    @PostMapping public ResponseEntity<Program> createProgram(@RequestBody Program program) { return ResponseEntity.ok(programService.createProgram(program)); }
    @GetMapping public ResponseEntity<List<Program>> getAllPrograms() { return ResponseEntity.ok(programService.getAllPrograms()); }
    @GetMapping("/{id}") public ResponseEntity<Program> getProgramById(@PathVariable Long id) { return programService.getProgramById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Program> updateProgram(@PathVariable Long id, @RequestBody Program program) { return programService.updateProgram(id, program).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteProgram(@PathVariable Long id) { return programService.softDeleteProgram(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
