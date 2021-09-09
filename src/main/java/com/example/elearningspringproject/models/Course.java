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
    @JsonIgnoreProperties("course")
    private List<CourseRate> ratings;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private List<CourseReview> courseReviews;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "overview", nullable = false)
    private String overview;

    @Column(name = "course_image")
    private String course_image;

    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    @Column(name = "date_of_creation", nullable = false)
    private Calendar date_of_creation = new GregorianCalendar();

    @Column(name = "estimated_duration", nullable = false)
    private String estimated_duration;

    @Column(name = "introduction_video")
    private String introduction_video;

    public Course(Instructor instructor, CourseCategory courseCategory, String title, Double price, String overview, String course_image, String difficulty, Calendar date_of_creation, String estimated_duration, String introduction_video) {
        this.instructor = instructor;
        this.courseCategory = courseCategory;
        this.title = title;
        this.price = price;
        this.overview = overview;
        this.course_image = course_image;
        this.difficulty = difficulty;
        this.date_of_creation = date_of_creation;
        this.estimated_duration = estimated_duration;
        this.introduction_video = introduction_video;
    }
}
