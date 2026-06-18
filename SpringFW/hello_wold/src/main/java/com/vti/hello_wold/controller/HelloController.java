package com.vti.hello_wold.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hello")
public class HelloController {
    @GetMapping
    public ResponseEntity<?> hello() {
        return new ResponseEntity<>("helloworld", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> hello(@PathVariable(name="id") String id) {
        return new ResponseEntity<>("hello "+id+" " , HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> hello2(@RequestParam(name="id") String id, @RequestParam(name="name") String name) {
        return new ResponseEntity<>("hello "+id+" "+name , HttpStatus.OK);
    }
}
