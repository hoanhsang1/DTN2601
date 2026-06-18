package com.vti.controller;

import com.vti.entity.Department;
import com.vti.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> findAll() {
        List<Department> departments = departmentService.findAll();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Department> findById(@PathVariable(name = "id") Integer id) {
        Department departments = departmentService.findById(id);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Department> findByName(@RequestParam String name) {
        Department departments = departmentService.findByName(name);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/searchNameAndId")
    public ResponseEntity<Department> findByNameAndId(@RequestParam String name, @RequestParam Integer id) {
        Department departments = departmentService.findByNameAndId(name,id);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) {
        departmentService.create(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Department> update(@RequestBody Department department, @PathVariable(name = "id") Integer id) {
        department.setId(id);
        departmentService.update(department);
        return new ResponseEntity<>(department,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Integer id) {
        departmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}