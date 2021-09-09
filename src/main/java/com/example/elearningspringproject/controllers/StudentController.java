package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Instructor;
import com.example.elearningspringproject.models.Student;
import com.example.elearningspringproject.repositories.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static final String DIRECTORY = "src/main/resources/static";

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("register")
    public ResponseEntity<HashMap<String, Object>> addStudent(@RequestParam("image") MultipartFile image,
                                                              @RequestParam("student")String studentInfos) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //Storing file in our Static folder
            long imageUploadDate = new GregorianCalendar().getTimeInMillis();
            String filename = String.format("%d%s", imageUploadDate, image.getOriginalFilename());

            Path imageStorage = Paths.get(DIRECTORY, filename.trim());
            Files.copy(image.getInputStream(), imageStorage, REPLACE_EXISTING);

            //Converting "studentInfos" string into a Student Object
            ObjectMapper mapper = new ObjectMapper();
            Student student = mapper.readValue(studentInfos, Student.class);

            student.setPhoto("http://localhost:8081/" + filename);
            student.setPassword(this.bCryptPasswordEncoder.encode(student.getPassword()));
            student.setRole("student");

            Student savedStudent = this.studentRepository.save(student);
            response.put("result", savedStudent);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping(path = "login")
    public ResponseEntity<Map<String, Object>> loginStudent(@RequestBody Student student) {

        HashMap<String, Object> response = new HashMap<>();

        Student studentFromDB = studentRepository.findByEmail(student.getEmail());

        if (studentFromDB == null) {
            response.put("message", "student not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {

            boolean compare = this.bCryptPasswordEncoder.matches(student.getPassword(), studentFromDB.getPassword());

            if (!compare) {
                response.put("message", "student not found !");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {

                if (!studentFromDB.getIsEnabled()) {
                    response.put("message", "student not allowed !");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                } else {
                    String token = Jwts.builder()
                            .claim("data", studentFromDB)
                            .signWith(SignatureAlgorithm.HS256, "SECRET")
                            .compact();

                    response.put("token", token);

                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            }
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = this.studentRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(students);
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

    @PutMapping("updateState/{id}")
    public ResponseEntity<HashMap<String, Object>> updateInstructorState(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if student exist, else findById returns an exception
            Student student = studentRepository.findById(id)
                    .orElseThrow( () -> new IllegalStateException("student with id: " + id + " not found"));

            Boolean state = student.getIsEnabled();
            student.setIsEnabled(!state);

            this.studentRepository.save(student);
            response.put("result", "State updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

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

    @GetMapping("{id}/enrollments")
    public ResponseEntity<HashMap<String, Object>> findStudentEnrollments(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Student student = this.studentRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("student with id: " + id + " not found"));
            response.put("result", student.getEnrollments());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("{id}/reviews")
    public ResponseEntity<HashMap<String, Object>> findStudentReviews(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Student student = this.studentRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("student with id: " + id + " not found"));
            response.put("result", student.getCourseReviews());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
