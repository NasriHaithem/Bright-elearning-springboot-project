package com.example.elearningspringproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instructor extends User{
    @OneToMany(mappedBy = "instructor")
    List<Course> courses;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    @Column(name = "photo", nullable = false)
    private String photo;

    @Column(name = "profession", nullable = false)
    private Boolean profession = true;

    @Column(name = "origin")
    private String origin;

    @Column(name = "phone")
    private Boolean phone = true;

    @Column(name = "brief_Introduction")
    private String brief_Introduction;

    @Column(name = "experience")
    private Boolean experience = true;

    @Column(name = "education")
    private String education;

    @Column(name = "facebook")
    private Boolean facebook = true;

    @Column(name = "linkedIn")
    private String linkedIn;

    @Column(name = "github")
    private String github;
}
