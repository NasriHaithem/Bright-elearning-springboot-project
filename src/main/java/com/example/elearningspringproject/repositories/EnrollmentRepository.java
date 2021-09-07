package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.Admin;
import com.example.elearningspringproject.models.Course;
import com.example.elearningspringproject.models.CourseStudentCompositeKey;
import com.example.elearningspringproject.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, CourseStudentCompositeKey> {
    List<Enrollment> findAllByCourse(Course course);
}
