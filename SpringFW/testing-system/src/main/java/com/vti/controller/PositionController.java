package com.vti.controller;

import com.vti.entity.Position;
import com.vti.service.IDepartmentService;
import com.vti.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @GetMapping
    public ResponseEntity<List<Position>> findAll() {
        List<Position> positions = positionService.findAll();
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Position> findById(@PathVariable(name = "id") Integer id) {
        Position positions = positionService.findById(id);
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Position> findByName(@RequestParam String name) {
        Position positions = positionService.findByName(name);
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping("/searchNameAndId")
    public ResponseEntity<Position> findByNameAndId(@RequestParam String name, @RequestParam Integer id) {
        Position positions = positionService.findByNameAndId(name,id);
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Position> create(@RequestBody Position position) {
        positionService.create(position);
        return new ResponseEntity<>(position, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Position> update(@RequestBody Position position, @PathVariable(name = "id") Integer id) {
        position.setId(id);
        positionService.update(position);
        return new ResponseEntity<>(position,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Integer id) {
        positionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}