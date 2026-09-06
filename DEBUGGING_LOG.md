# University ERP Debugging Log

This log records implementation issues, expected HTTP status codes, causes, and fixes required by the project specification.

| Error / scenario | HTTP status | Cause | Fix |
|---|---:|---|---|
| Resource ID does not exist or is soft-deleted | 404 | Reads must ignore `isActive=false` records | Added `findByIdAndIsActiveTrue` and `ResourceNotFoundException` handling |
| Blank or oversized DTO field | 400 | Invalid request data | Added Jakarta Validation constraints matching entity column sizes and `@Valid` on create/update endpoints |
| Invalid email | 400 | Instructor email format is invalid | Added `@Email` on `InstructorDTO.email` |
| Duplicate course code | 400 | `Course.courseCode` is unique | Existing service uniqueness validation retained |
| Duplicate instructor email | 400 | `Instructor.email` is unique | Existing service uniqueness validation retained |
| Student already enrolled | 400 | Duplicate active `ENROLLED` enrollment | Added enrollment existence check before save |
| Course is full | 400 | Active enrolled count reached capacity | Added enrolled-count check against active classroom capacity |
| No classroom capacity available | 400 | Entity model does not directly link Course to Classroom | Because the supplied entity table says Classroom belongs only to Department, capacity is derived from the largest active classroom in the course's department without adding an unsupported Course-Classroom relationship |
| Exam date today or in the past | 400 | Exams must be future-dated | Added `@Future` plus service-level date validation |
| Grade references exam from another course | 400 | Enrollment course and exam course differ | Added cross-entity validation in `GradeService` |
| Grade exceeds exam total marks | 400 | Score is outside exam range | Added score <= `Exam.totalMarks` validation |
| Duplicate grade for same enrollment/exam | 400 | One grade should represent one exam result | Added repository existence check before grade creation |
| Cyclic / infinite JSON | N/A | Bidirectional JPA relationships recursively serialize | Entity back-references use `@JsonIgnore`; controllers now return DTOs instead of entities |
| Sensitive phone/contact fields leaked in responses | N/A | Raw entity serialization exposes contact data | DTO responses omit phone numbers; input phone fields are write-only |
| Validation exception produced framework default body | 400 | No global handler | Added `GlobalExceptionHandler` and record-based `ErrorResponse` |
| Unexpected server failure | 500 | Unhandled exception | Added generic fallback in `GlobalExceptionHandler` |

## GPA-style classification used

The specification requests a GPA-style classification but does not define a scale. The implementation normalizes every grade to a percentage using `score / totalMarks * 100`, averages those percentages, then uses: 90+ = 4.0 Excellent, 80–89.99 = 3.0 Very Good, 70–79.99 = 2.0 Good, 60–69.99 = 1.0 Pass, below 60 = 0.0 Fail.

## Manual verification still required

The database-schema screenshot must be captured from the user's running MySQL Workbench after Hibernate generates the schema. The generated Postman collection should be run against the local database so actual response screenshots/results can be attached as evaluation evidence.
