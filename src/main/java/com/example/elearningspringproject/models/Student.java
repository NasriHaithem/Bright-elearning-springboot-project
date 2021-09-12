package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Student extends User{
    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    private List<CourseRate> ratings;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    private List<CourseReview> courseReviews;

    @Override
    public String toString() {
        return "Student{" +
                "isEnabled=" + isEnabled +
                ", photo='" + photo + '\'' +
                ", ratings=" + ratings +
                ", enrollments=" + enrollments +
                ", courseReviews=" + courseReviews +
                '}';
    }
}
