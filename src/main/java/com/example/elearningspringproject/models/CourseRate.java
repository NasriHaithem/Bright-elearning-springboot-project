package com.example.elearningspringproject.models;

import javax.persistence.*;

@Entity
public class CourseRate {
    @EmbeddedId
    CourseStudentCompositeKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;

    int rating;
}
