package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.dto.InstructorStatsDTO;
import com.codelegends.UniversityERP.dto.ProgramStatsDTO;
import com.codelegends.UniversityERP.dto.UniversityStatsDTO;
import com.codelegends.UniversityERP.dto.UniversitySummaryDTO;
import com.codelegends.UniversityERP.services.StatsService;
import com.codelegends.UniversityERP.services.SummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;
    private final SummaryService summaryService;

    public StatsController(StatsService statsService, SummaryService summaryService) {
        this.statsService = statsService;
        this.summaryService = summaryService;
    }

    @GetMapping("/universities/{universityId}")
    public ResponseEntity<UniversityStatsDTO> getUniversityStats(@PathVariable Long universityId) {
        return ResponseEntity.ok(statsService.getUniversityStats(universityId));
    }

    @GetMapping("/instructors/{instructorId}")
    public ResponseEntity<InstructorStatsDTO> getInstructorStats(@PathVariable Long instructorId) {
        return ResponseEntity.ok(statsService.getInstructorStats(instructorId));
    }

    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramStatsDTO> getProgramStats(@PathVariable Long programId) {
        return ResponseEntity.ok(statsService.getProgramStats(programId));
    }

    @GetMapping("/summary")
    public ResponseEntity<UniversitySummaryDTO> getUniversityWideSummary() {
        return ResponseEntity.ok(summaryService.getSummary());
    }
}
