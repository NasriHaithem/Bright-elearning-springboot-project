package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Integer> {
}
