package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
}
