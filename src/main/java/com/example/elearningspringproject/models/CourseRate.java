package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRate {
    @Override
    public String toString() {
        return "CourseRate{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", rating=" + rating +
                '}';
    }

    @EmbeddedId
    private CourseStudentCompositeKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"courseReviews", "enrollments", "ratings", "modules"})
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties({"courseReviews", "enrollments", "ratings", "modules"})
    private Course course;

    private int rating;
}
