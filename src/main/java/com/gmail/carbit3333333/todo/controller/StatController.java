package com.gmail.carbit3333333.todo.controller;

import com.gmail.carbit3333333.todo.entity.Stat;
import com.gmail.carbit3333333.todo.repository.StatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController {
    private final StatRepository repository;
    private final Long defaultId = 1L;

    public StatController(StatRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/stat")
    public ResponseEntity<Stat>findById(){
        return ResponseEntity.ok(repository.findById(defaultId).get());
    }
}
