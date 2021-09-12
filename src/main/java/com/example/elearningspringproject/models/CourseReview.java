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
public class CourseReview {
    @Override
    public String toString() {
        return "CourseReview{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", review_date=" + review_date +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"courseReviews", "enrollments", "ratings"})
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties({"courseReviews", "enrollments", "ratings", "modules"})
    private Course course;

    private Calendar review_date = new GregorianCalendar();
    private String comment;
}
