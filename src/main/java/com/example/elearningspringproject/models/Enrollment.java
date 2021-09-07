package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Enrollment {
    @EmbeddedId
    private CourseStudentCompositeKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties("enrollments")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties("enrollments")
    private Course course;

    private Calendar enrollment_date = new GregorianCalendar();
    private float progress = 0;
}
