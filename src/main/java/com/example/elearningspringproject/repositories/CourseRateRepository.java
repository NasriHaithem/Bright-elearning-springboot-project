package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.CourseRate;
import com.example.elearningspringproject.models.CourseStudentCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRateRepository extends JpaRepository<CourseRate, CourseStudentCompositeKey>  {
}
