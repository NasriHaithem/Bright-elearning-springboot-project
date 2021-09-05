package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "modules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Module {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @ManyToOne
    @JoinColumn(name = "idCourse")
    private Course course;

    @OneToMany(mappedBy = "module")
    @JsonIgnoreProperties("module")
    private List<Lesson> lessons;
}
