package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseReview {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties("courseReviews")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties("courseReviews")
    private Course course;

    private Calendar review_date = new GregorianCalendar();
    private String comment;
}
