package com.gmail.carbit3333333.todo.controller;

import com.gmail.carbit3333333.todo.entity.Category;
import com.gmail.carbit3333333.todo.entity.Priority;
import com.gmail.carbit3333333.todo.repository.PriorityRepository;
import com.gmail.carbit3333333.todo.search.CategorySearchValues;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @GetMapping("/all")
    public List<Priority> findAll(){
        return repository.findAllByOrderByIdAsc();
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Priority>deleteByid(@PathVariable Long id){
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id=" +id+" not found ", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Priority>>search(@RequestBody CategorySearchValues categorySearchValues){
        System.out.println(categorySearchValues.getText());
        return ResponseEntity.ok(repository.findByTitle(categorySearchValues.getText()));
    }
}
