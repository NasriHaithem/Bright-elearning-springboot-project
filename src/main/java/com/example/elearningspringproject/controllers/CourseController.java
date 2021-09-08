package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Course;
import com.example.elearningspringproject.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("courses")
public class CourseController {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("add")
    public ResponseEntity<HashMap<String, Object>> addCourse(@RequestBody Course course) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            System.out.println(course.getInstructor());
            System.out.println(course.getCourseCategory());
            Course savedCourse = this.courseRepository.save(course);
            response.put("result", savedCourse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString() +"//// \n"+ e.getMessage() +"//// \n" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = this.courseRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @GetMapping("{id}")
    public ResponseEntity<HashMap<String, Object>> findCourseById(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Course course = this.courseRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("course with id: " + id + " not found"));
            response.put("result", course);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.courseRepository.deleteById(id);
            response.put("result", "Course deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateCourse(@RequestBody Course course) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if course exist, else findById returns an exception
            courseRepository.findById(course.getId());
            Course courseToUpdate= this.courseRepository.save(course);
            response.put("result", courseToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

}
