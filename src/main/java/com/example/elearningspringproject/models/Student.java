package com.example.elearningspringproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    @Column(name = "photo", nullable = false)
    private String photo;

    @OneToMany(mappedBy = "student")
    Set<CourseRate> ratings;

    @OneToMany(mappedBy = "student")
    Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "student")
    Set<CourseReview> courseReviews;
}
