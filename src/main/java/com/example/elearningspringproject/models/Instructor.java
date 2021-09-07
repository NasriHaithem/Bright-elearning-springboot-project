package com.example.elearningspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
    @JsonIgnore
    List<Course> courses;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true ;

    @Column(name = "photo")
    private String photo;

    @Column(name = "profession", nullable = false)
    private String profession;

    @Column(name = "origin")
    private String origin;

    @Column(name = "phone")
    private String phone;

    @Column(name = "brief_Introduction")
    private String brief_Introduction;

    @Column(name = "experience")
    private String experience;

    @Column(name = "education")
    private String education;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "linkedIn")
    private String linkedIn;

    @Column(name = "github")
    private String github;

    @Override
    public String toString() {
        return "Instructor{" +
                super.toString() +
                "courses=" + courses +
                ", isEnabled=" + isEnabled +
                ", photo='" + photo + '\'' +
                ", profession='" + profession + '\'' +
                ", origin='" + origin + '\'' +
                ", phone='" + phone + '\'' +
                ", brief_Introduction='" + brief_Introduction + '\'' +
                ", experience='" + experience + '\'' +
                ", education='" + education + '\'' +
                ", facebook='" + facebook + '\'' +
                ", linkedIn='" + linkedIn + '\'' +
                ", github='" + github + '\'' +
                '}';
    }
}
