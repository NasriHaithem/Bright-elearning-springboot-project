package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "modules")
public class Module {
    @Id
    @GeneratedValue
    Integer id;

    @Column(name = "module_name", nullable = false)
    String moduleName;

    @ManyToOne
    @JoinColumn(name = "idCourse")
    public Course course;

    @OneToMany(mappedBy = "module")
    @JsonIgnoreProperties("module")
    public List<Lesson> lessons;
}
