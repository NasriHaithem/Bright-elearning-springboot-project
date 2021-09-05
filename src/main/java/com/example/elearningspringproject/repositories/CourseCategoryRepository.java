package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Integer> {
}
