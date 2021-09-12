package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", lessonName='" + lessonName + '\'' +
                ", description='" + description + '\'' +
                ", lessonVideo='" + lessonVideo + '\'' +
                ", seen=" + seen +
                ", module=" + module +
                '}';
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "lesson_name", nullable = false)
    private String lessonName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "lesson_video", nullable = false)
    private String lessonVideo;

    @Column(name = "seen", nullable = false)
    private Boolean seen = false;

    @ManyToOne
    @JoinColumn(name = "idModule")
    @JsonIgnoreProperties("lessons")
    private Module module;
}
