package com.example.elearningspringproject.repositories;

import com.example.elearningspringproject.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
}
