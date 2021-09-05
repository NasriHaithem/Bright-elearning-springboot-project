package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Student;
import com.example.elearningspringproject.repositories.StudentRepository;
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
@RequestMapping("students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("register")
    public ResponseEntity<HashMap<String, Object>> addStudent(@RequestBody Student student) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            student.setPassword(this.bCryptPasswordEncoder.encode(student.getPassword()));
            Student savedStudent = this.studentRepository.save(student);
            response.put("result", savedStudent);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = this.studentRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @GetMapping("{id}")
    public ResponseEntity<HashMap<String, Object>> findStudentById(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Student student = this.studentRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("student with id: " + id + " not found"));
            response.put("result", student);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.studentRepository.deleteById(id);
            response.put("result", "Student deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateStudent(@RequestBody Student student) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if student exist, else findById returns an exception
            studentRepository.findById(student.getId());
            student.setPassword(this.bCryptPasswordEncoder.encode(student.getPassword()));
            Student studentToUpdate= this.studentRepository.save(student);
            response.put("result", studentToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}
