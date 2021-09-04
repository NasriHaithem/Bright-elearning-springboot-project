package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idInstructor")
    Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "idCourseCategory")
    CourseCategory courseCategory;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    public List<Module> modules;

    @OneToMany(mappedBy = "course")
    Set<CourseRate> ratings;

    @OneToMany(mappedBy = "course")
    Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    Set<CourseReview> courseReviews;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "overview", nullable = false)
    private Double overview;

    @Column(name = "course_image", nullable = false)
    private Double course_image;

    @Column(name = "difficulty", nullable = false)
    private Double difficulty;

    @Column(name = "date_of_creation", nullable = false)
    private Double date_of_creation;

    @Column(name = "estimated_duration", nullable = false)
    private Double estimated_duration;

    @Column(name = "introduction_video")
    private Double introduction_video;
}
