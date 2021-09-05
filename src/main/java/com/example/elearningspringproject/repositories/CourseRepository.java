package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
