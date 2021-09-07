package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByEmail(String email);
}
