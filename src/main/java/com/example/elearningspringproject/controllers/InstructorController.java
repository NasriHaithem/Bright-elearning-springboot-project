package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Instructor;
import com.example.elearningspringproject.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("instructors")
public class InstructorController {
    private final InstructorRepository instructorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public InstructorController(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @PostMapping("register")
    public ResponseEntity<HashMap<String, Object>> addInstructor(@RequestBody Instructor instructor) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            instructor.setPassword(this.bCryptPasswordEncoder.encode(instructor.getPassword()));
            Instructor savedInstructor = this.instructorRepository.save(instructor);
            response.put("result", savedInstructor);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructors = this.instructorRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(instructors);
    }

    @GetMapping("{id}")
    public ResponseEntity<HashMap<String, Object>> findInstructorById(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Instructor instructor = this.instructorRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("instructor with id: " + id + " not found"));
            response.put("result", instructor);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteInstructor(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.instructorRepository.deleteById(id);
            response.put("result", "Instructor deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateInstructor(@RequestBody Instructor instructor) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if instructor exist, else findById returns an exception
            instructorRepository.findById(instructor.getId());
            instructor.setPassword(this.bCryptPasswordEncoder.encode(instructor.getPassword()));
            Instructor instructorToUpdate= this.instructorRepository.save(instructor);
            response.put("result", instructorToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

}
