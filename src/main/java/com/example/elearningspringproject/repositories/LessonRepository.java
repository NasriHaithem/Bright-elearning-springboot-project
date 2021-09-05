package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
