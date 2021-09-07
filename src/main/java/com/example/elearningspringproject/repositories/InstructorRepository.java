package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Instructor findByEmail(String email);
}
