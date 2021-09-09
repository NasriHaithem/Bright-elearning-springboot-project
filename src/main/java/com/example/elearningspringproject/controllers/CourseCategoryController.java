package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.CourseCategory;
import com.example.elearningspringproject.repositories.CourseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CourseCategoryController {
    private final CourseCategoryRepository courseCategoryRepository;

    @Autowired
    public CourseCategoryController(CourseCategoryRepository courseCategoryRepository) {
        this.courseCategoryRepository = courseCategoryRepository;
    }


    @PostMapping("add")
    public ResponseEntity<HashMap<String, Object>> addCourseCategory(@RequestBody CourseCategory courseCategory) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            CourseCategory savedCourseCategory = this.courseCategoryRepository.save(courseCategory);
            response.put("result", savedCourseCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<CourseCategory>> getAllCourseCategorys() {
        List<CourseCategory> courseCategorys = this.courseCategoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(courseCategorys);
    }

    @GetMapping("{id}")
    public ResponseEntity<HashMap<String, Object>> findCourseCategoryById(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            CourseCategory courseCategory = this.courseCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("courseCategory with id: " + id + " not found"));
            response.put("result", courseCategory);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourseCategory(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.courseCategoryRepository.deleteById(id);
            response.put("result", "CourseCategory deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateCourseCategory(@RequestBody CourseCategory courseCategory) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if courseCategory exist, else findById returns an exception
            courseCategoryRepository.findById(courseCategory.getId());
            CourseCategory courseCategoryToUpdate= this.courseCategoryRepository.save(courseCategory);
            response.put("result", courseCategoryToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}
