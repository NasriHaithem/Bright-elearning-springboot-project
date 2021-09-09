package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Course;
import com.example.elearningspringproject.models.Instructor;
import com.example.elearningspringproject.repositories.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    public static final String DIRECTORY = "src/main/resources/static";

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("add")
    public ResponseEntity<HashMap<String, Object>> addCourse(@RequestParam("image") MultipartFile image,
                                                             @RequestParam("video")MultipartFile video,
                                                             @RequestParam("course")String courseInfos) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            long imageUploadDate = new GregorianCalendar().getTimeInMillis();
            long videoUploadDate = new GregorianCalendar().getTimeInMillis();

            //Storing image in our Static folder
            String imageName = String.format("%d%s", imageUploadDate, image.getOriginalFilename());
            Path imageStorage = Paths.get(DIRECTORY, imageName.trim());
            Files.copy(image.getInputStream(), imageStorage, REPLACE_EXISTING);

            //Storing video in our Static folder
            String videoName = String.format("%d%s", videoUploadDate, video.getOriginalFilename());
            Path videoStorage = Paths.get(DIRECTORY, videoName.trim());
            Files.copy(video.getInputStream(), videoStorage, REPLACE_EXISTING);

            //Converting "instructorInfos" string into a Instructor Object
            ObjectMapper mapper = new ObjectMapper();
            Course course = mapper.readValue(courseInfos, Course.class);

            course.setCourse_image("http://localhost:8081/" + imageName);
            course.setIntroduction_video("http://localhost:8081/" + videoName);

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
