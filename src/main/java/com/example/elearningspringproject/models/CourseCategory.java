package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCategory {
    @Id
    @GeneratedValue
    private Integer id;

    @Override
    public String toString() {
        return "CourseCategory{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", courses=" + courses +
                '}';
    }

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "courseCategory")
    @JsonIgnoreProperties("courseCategory")
    private List<Course> courses;
}
