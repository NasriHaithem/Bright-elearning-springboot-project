package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idInstructor")
    @JsonIgnoreProperties("courses")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "idCourseCategory")
    @JsonIgnoreProperties("courses")
    private CourseCategory courseCategory;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private List<Module> modules;

    @OneToMany(mappedBy = "course")
    private List<CourseRate> ratings;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    private List<CourseReview> courseReviews;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "overview", nullable = false)
    private String overview;

    @Column(name = "course_image", nullable = false)
    private String course_image;

    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    @Column(name = "date_of_creation", nullable = false)
    private Calendar date_of_creation = new GregorianCalendar();

    @Column(name = "estimated_duration", nullable = false)
    private String estimated_duration;

    @Column(name = "introduction_video")
    private String introduction_video;
}
