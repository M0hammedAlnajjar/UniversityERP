package com.codelegends.UniversityERP.controllers;

import com.codelegends.UniversityERP.entities.Course;
import com.codelegends.UniversityERP.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService) { this.courseService = courseService; }
    @PostMapping public ResponseEntity<Course> createCourse(@RequestBody Course course) { return ResponseEntity.ok(courseService.createCourse(course)); }
    @GetMapping public ResponseEntity<List<Course>> getAllCourses() { return ResponseEntity.ok(courseService.getAllCourses()); }
    @GetMapping("/{id}") public ResponseEntity<Course> getCourseById(@PathVariable Long id) { return courseService.getCourseById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) { return courseService.updateCourse(id, course).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deleteCourse(@PathVariable Long id) { return courseService.softDeleteCourse(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
