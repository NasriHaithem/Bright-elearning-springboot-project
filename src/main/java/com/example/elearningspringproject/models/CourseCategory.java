package com.example.elearningspringproject.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class CourseCategory {
    @Id
    @GeneratedValue
    Integer id;

    @Column(name = "category_name", nullable = false)
    String categoryName;

    @OneToMany(mappedBy = "courseCategory")
    List<Course> courses;
}
