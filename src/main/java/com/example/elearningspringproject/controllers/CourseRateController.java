package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.CourseStudentCompositeKey;
import com.example.elearningspringproject.models.CourseRate;
import com.example.elearningspringproject.repositories.CourseRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/course-rates")
public class CourseRateController {
    private final CourseRateRepository courseRateRepository;

    @Autowired
    public CourseRateController(CourseRateRepository courseRateRepository) {
        this.courseRateRepository = courseRateRepository;
    }

    @PostMapping("add")
    public ResponseEntity<HashMap<String, Object>> addCourseRate(@RequestBody CourseRate courseRate) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            CourseRate savedCourseRate = this.courseRateRepository.save(courseRate);
            response.put("result", savedCourseRate);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<CourseRate>> getAllCourseRates() {
        List<CourseRate> courseRates = this.courseRateRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(courseRates);
    }


    @GetMapping("{studentId}/{courseId}")
    public ResponseEntity<HashMap<String, Object>> findCourseRateById(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        HashMap<String, Object> response = new HashMap<>();
        try {

            CourseRate courseRate = this.courseRateRepository.findById(new CourseStudentCompositeKey(studentId, courseId))
                    .orElseThrow(() -> new IllegalStateException(
                            "courseRate with composite id: (studentId: " + studentId + ", courseId:"+ courseId + ") not found"));
            response.put("result", courseRate);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{studentId}/{courseId}")
    public ResponseEntity<Map<String, Object>> deleteCourseRate(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.courseRateRepository.deleteById(new CourseStudentCompositeKey(studentId, courseId));
            response.put("result", "CourseRate deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateCourseRate(@RequestBody CourseRate courseRate) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if courseRate exist, else findById returns an exception
            courseRateRepository.findById(courseRate.getId());
            CourseRate courseRateToUpdate= this.courseRateRepository.save(courseRate);
            response.put("result", courseRateToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}
