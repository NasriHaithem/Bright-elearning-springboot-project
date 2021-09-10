package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Admin;
import com.example.elearningspringproject.models.Admin;
import com.example.elearningspringproject.repositories.AdminRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
@RequestMapping("/admins")
public class AdminController {
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostMapping("register")
    public ResponseEntity<HashMap<String, Object>> addAdmin(@RequestBody Admin admin) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            admin.setPassword(this.bCryptPasswordEncoder.encode(admin.getPassword()));
            admin.setRole("admin");

            Admin savedAdmin = this.adminRepository.save(admin);
            response.put("result", savedAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping(path = "login")
    public ResponseEntity<Map<String, Object>> loginAdmin(@RequestBody Admin admin) {

        HashMap<String, Object> response = new HashMap<>();

        Admin adminFromDB = adminRepository.findByEmail(admin.getEmail());

        if (adminFromDB == null) {
            response.put("message", "admin not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {

            boolean compare = this.bCryptPasswordEncoder.matches(admin.getPassword(), adminFromDB.getPassword());

            if (!compare) {
                response.put("message", "admin not found !");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }else {
                String token = Jwts.builder()
                        .claim("data", adminFromDB)
                        .signWith(SignatureAlgorithm.HS256, "SECRET")
                        .compact();

                response.put("token", token);

                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = this.adminRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(admins);
    }

    @GetMapping("{id}")
    public ResponseEntity<HashMap<String, Object>> findAdminById(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Admin admin = this.adminRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("admin with id: " + id + " not found"));
            response.put("result", admin);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteAdmin(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.adminRepository.deleteById(id);
            response.put("result", "Admin deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateAdmin(@RequestBody Admin admin) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if admin exist, else findById returns an exception
            adminRepository.findById(admin.getId());
            admin.setPassword(this.bCryptPasswordEncoder.encode(admin.getPassword()));
            Admin adminToUpdate= this.adminRepository.save(admin);
            response.put("result", adminToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}
