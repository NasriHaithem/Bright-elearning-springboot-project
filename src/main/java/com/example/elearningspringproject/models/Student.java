package com.example.elearningspringproject.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student extends User{
    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "student")
    private List<CourseRate> ratings;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "student")
    private List<CourseReview> courseReviews;
}
