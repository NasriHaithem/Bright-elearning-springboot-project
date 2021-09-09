package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Course;
import com.example.elearningspringproject.models.CourseStudentCompositeKey;
import com.example.elearningspringproject.models.Enrollment;
import com.example.elearningspringproject.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentController(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @PostMapping("add")
    public ResponseEntity<HashMap<String, Object>> addEnrollment(@RequestBody Enrollment enrollment) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Enrollment savedEnrollment = this.enrollmentRepository.save(enrollment);
            response.put("result", savedEnrollment);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = this.enrollmentRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(enrollments);
    }


    @GetMapping("{studentId}/{courseId}")
    public ResponseEntity<HashMap<String, Object>> findEnrollmentById(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        HashMap<String, Object> response = new HashMap<>();
        try {

            Enrollment enrollment = this.enrollmentRepository.findById(new CourseStudentCompositeKey(studentId, courseId))
                    .orElseThrow(() -> new IllegalStateException(
                            "enrollment with composite id: (studentId: " + studentId + ", courseId:"+ courseId + ") not found"));
            response.put("result", enrollment);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{studentId}/{courseId}")
    public ResponseEntity<Map<String, Object>> deleteEnrollment(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.enrollmentRepository.deleteById(new CourseStudentCompositeKey(studentId, courseId));
            response.put("result", "Enrollment deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateEnrollment(@RequestBody Enrollment enrollment) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if enrollment exist, else findById returns an exception
            enrollmentRepository.findById(enrollment.getId());
            Enrollment enrollmentToUpdate= this.enrollmentRepository.save(enrollment);
            response.put("result", enrollmentToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}
