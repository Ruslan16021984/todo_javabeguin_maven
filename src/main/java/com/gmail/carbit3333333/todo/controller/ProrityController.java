package com.gmail.carbit3333333.todo.controller;

import com.gmail.carbit3333333.todo.entity.Category;
import com.gmail.carbit3333333.todo.entity.Priority;
import com.gmail.carbit3333333.todo.repository.PriorityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority){
        if (priority.getId()!=null && priority.getId()!=0){
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle()==null || priority.getTitle().trim().length()==0){
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor()==null || priority.getColor().trim().length()==0){
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }
       return ResponseEntity.ok(repository.save(priority));
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Priority priority){
        if (priority.getId()==null || priority.getId()==0){
            return new ResponseEntity("redundant param: id ", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle()==null || priority.getTitle().trim().length()==0){
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor()==null || priority.getColor().trim().length()==0){
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(repository.save(priority));
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id){
        Priority priority = null;
        try {
            priority = repository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id +"not found ", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }
}
