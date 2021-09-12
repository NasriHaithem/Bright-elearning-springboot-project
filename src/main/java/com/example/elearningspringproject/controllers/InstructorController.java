package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.*;
import com.example.elearningspringproject.models.Instructor;
import com.example.elearningspringproject.repositories.InstructorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@CrossOrigin
@RestController
@RequestMapping("/instructors")
public class InstructorController {
    private final InstructorRepository instructorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static final String DIRECTORY = "src/main/resources/static";

    @Autowired
    public InstructorController(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> addInstructor(@RequestParam("image") MultipartFile image,
                                                              @RequestParam("instructor")String instructorInfos) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //Converting "instructorInfos" string into a Instructor Object
            ObjectMapper mapper = new ObjectMapper();
            Instructor instructor = mapper.readValue(instructorInfos, Instructor.class);

            //check if instructor exist, else findById returns an exception
            instructorRepository.findById(instructor.getId())
                    .orElseThrow( () -> new IllegalStateException("instructor  not found") );

            //Storing file in our Static folder
            long imageUploadDate = new GregorianCalendar().getTimeInMillis();
            String filename = String.format("%d%s", imageUploadDate, image.getOriginalFilename());

            Path imageStorage = Paths.get(DIRECTORY, filename.trim());
            Files.copy(image.getInputStream(), imageStorage, REPLACE_EXISTING);


            instructor.setPhoto("http://localhost:8081/" + filename);
            if (instructor.getPassword() != null) {
                instructor.setPassword(this.bCryptPasswordEncoder.encode(instructor.getPassword()));
            }

            Instructor savedInstructor = this.instructorRepository.save(instructor);
            response.put("result", savedInstructor);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping(path = "login")
    public ResponseEntity<Map<String, Object>> loginInstructor(@RequestBody Instructor instructor) {

        HashMap<String, Object> response = new HashMap<>();

        Instructor instructorFromDB = instructorRepository.findByEmail(instructor.getEmail());

        if (instructorFromDB == null) {
            response.put("message", "instructor not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {

            boolean compare = this.bCryptPasswordEncoder.matches(instructor.getPassword(), instructorFromDB.getPassword());

            if (!compare) {
                response.put("message", "instructor not found !");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {

                if (!instructorFromDB.getIsEnabled()) {
                    response.put("message", "instructor not allowed !");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                } else {
                    String token = Jwts.builder()
                            .claim("data", instructorFromDB)
                            .signWith(SignatureAlgorithm.HS256, "SECRET")
                            .compact();

                    response.put("token", token);

                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            }
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructors = this.instructorRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(instructors);
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

    @PostMapping("register")
    public ResponseEntity<HashMap<String, Object>> updateInstructor(@RequestBody Instructor instructor) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            instructor.setPassword(this.bCryptPasswordEncoder.encode(instructor.getPassword()));
            instructor.setRole("instructor");

            Instructor instructorToUpdate= this.instructorRepository.save(instructor);
            response.put("result", instructorToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @PatchMapping("updateState/{id}")
    public ResponseEntity<HashMap<String, Object>> updateInstructorState(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if instructor exist, else findById returns an exception
            Instructor instructor = instructorRepository.findById(id)
                    .orElseThrow( () -> new IllegalStateException("instructor with id: " + id + " not found"));

            Boolean state = instructor.getIsEnabled();
            instructor.setIsEnabled(!state);

            instructorRepository.save(instructor);
            response.put("result", "State updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

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

    @GetMapping("{id}/courses")
    public ResponseEntity<HashMap<String, Object>> findInstructorCourses(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Instructor instructor = this.instructorRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("instructor with id: " + id + " not found"));

            ArrayList<HashMap<String, Object>> result = new ArrayList<>();
            List<Course> myCourses = instructor.getCourses();
            for (Course course : myCourses) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("id",course.getId());
                data.put("image",course.getCourse_image());
                data.put("title",course.getTitle());
                data.put("category",course.getCourseCategory().getCategoryName());
                data.put("nbrOfEnrollments",course.getEnrollments().size());
                data.put("price",course.getPrice());
                result.add(data);
            }
            response.put("result", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("{id}/students")
    public ResponseEntity<HashMap<String, Object>> findInstructorStudents(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //find instructor by ID
            Instructor instructor = this.instructorRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("instructor with id: " + id + " not found"));

            //find students enrolled in the instructor courses
            ArrayList<Enrollment> enrolledStudentsList = new ArrayList<>();
            for (Course cours : instructor.getCourses()) {
                enrolledStudentsList.addAll(cours.getEnrollments());
            }

            //respond with enrolledStudentsList
            response.put("result", enrolledStudentsList);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
