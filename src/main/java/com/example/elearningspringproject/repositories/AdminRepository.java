package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
