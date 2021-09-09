package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.CourseReview;
import com.example.elearningspringproject.repositories.CourseReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/course-reviews")
public class CourseReviewController {
    private final CourseReviewRepository courseReviewRepository;

    @Autowired
    public CourseReviewController(CourseReviewRepository courseReviewRepository) {
        this.courseReviewRepository = courseReviewRepository;
    }

    @PostMapping("add")
    public ResponseEntity<HashMap<String, Object>> addCourseReview(@RequestBody CourseReview courseReview) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            CourseReview savedCourseReview = this.courseReviewRepository.save(courseReview);
            response.put("result", savedCourseReview);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<CourseReview>> getAllCourseReviews() {
        List<CourseReview> courseReviews = this.courseReviewRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(courseReviews);
    }


    @GetMapping("{id}")
    public ResponseEntity<HashMap<String, Object>> findCourseReviewById(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            CourseReview courseReview = this.courseReviewRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("courseReview with id: " + id + " not found"));
            response.put("result", courseReview);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourseReview(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.courseReviewRepository.deleteById(id);
            response.put("result", "CourseReview deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateCourseReview(@RequestBody CourseReview courseReview) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if courseReview exist, else findById returns an exception
            courseReviewRepository.findById(courseReview.getId());
            CourseReview courseReviewToUpdate= this.courseReviewRepository.save(courseReview);
            response.put("result", courseReviewToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}
