package com.example.elearningspringproject.controllers;

import com.example.elearningspringproject.models.Module;
import com.example.elearningspringproject.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("modules")
public class ModuleController {
    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleController(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @PostMapping("add")
    public ResponseEntity<HashMap<String, Object>> addModule(@RequestBody Module module) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Module savedModule = this.moduleRepository.save(module);
            response.put("result", savedModule);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Module>> getAllModules() {
        List<Module> modules = this.moduleRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(modules);
    }


    @GetMapping("{id}")
    public ResponseEntity<HashMap<String, Object>> findModuleById(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Module module = this.moduleRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("module with id: " + id + " not found"));
            response.put("result", module);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteModule(@PathVariable Integer id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            this.moduleRepository.deleteById(id);
            response.put("result", "Module deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("update")
    public ResponseEntity<HashMap<String, Object>> updateModule(@RequestBody Module module) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            //check if module exist, else findById returns an exception
            moduleRepository.findById(module.getId());
            Module moduleToUpdate= this.moduleRepository.save(module);
            response.put("result", moduleToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}
