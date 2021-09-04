package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue
    Integer id;

    @Column(name = "lesson_name", nullable = false)
    String lessonName;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "lesson_video", nullable = false)
    String lessonVideo;

    @ManyToOne
    @JoinColumn(name = "idModule")
    public Module module;
}
