package com.gmail.carbit3333333.todo.controller;

import com.gmail.carbit3333333.todo.entity.Priority;
import com.gmail.carbit3333333.todo.repository.PriorityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/priority")
public class ProrityController {

    private PriorityRepository repository;

    public ProrityController(PriorityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/test")
    public List<Priority> test(){
        List<Priority> priorityList = repository.findAll();
        System.out.println(priorityList.toString());
        return priorityList;
    }
}
