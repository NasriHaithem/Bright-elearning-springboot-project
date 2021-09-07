package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Lesson;
import com.example.elearningspringproject.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("lessons")
public class LessonController {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @PostMapping("add")
    public ResponseEntity<HashMap<String, Object>> addLesson(@RequestBody Lesson lesson) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Lesson savedLesson = this.lessonRepository.save(lesson);
            response.put("result", savedLesson);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        List<Lesson> lessons = this.lessonRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }


    @GetMapping("{id}")
    public ResponseEntity<HashMap<String, Object>> findLessonById(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Lesson lesson = this.lessonRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("lesson with id: " + id + " not found"));
            response.put("result", lesson);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteLesson(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.lessonRepository.deleteById(id);
            response.put("result", "Lesson deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateLesson(@RequestBody Lesson lesson) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if lesson exist, else findById returns an exception
            lessonRepository.findById(lesson.getId());
            Lesson lessonToUpdate= this.lessonRepository.save(lesson);
            response.put("result", lessonToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}
