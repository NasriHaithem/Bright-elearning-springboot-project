package com.example.elearningspringproject.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
public class CourseReview {
    @Id
    @GeneratedValue
    Integer id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;

    Calendar review_date = new GregorianCalendar();
    String comment;
}
