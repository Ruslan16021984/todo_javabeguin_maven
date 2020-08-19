package com.gmail.carbit3333333.todo.controller;

import com.gmail.carbit3333333.todo.entity.Category;
import com.gmail.carbit3333333.todo.repository.CategoryRepository;
import com.gmail.carbit3333333.todo.search.CategorySearchValues;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<Category> findAll() {
        System.out.println("CategoryController:findAll----------------------");
        return repository.findAllByOrderByTitleAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        System.out.println("CategoryController:add----------------------");
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(repository.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {
        System.out.println("CategoryController:update----------------------");
        if (category.getId() != null || category.getId() != 0) {
            return new ResponseEntity("redundant param: id ", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(repository.save(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        System.out.println("CategoryController:{id}----------------------");
        Category category = null;
        try {
            category = repository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id +"not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category>deleteByid(@PathVariable Long id){
        System.out.println("CategoryController:delete/{id}----------------------");
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id=" +id+" not found ", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Category>>search(@RequestBody CategorySearchValues categorySearchValues){
        System.out.println("CategoryController:search----------------------");
        System.out.println(categorySearchValues.getText());
        return ResponseEntity.ok(repository.findByTitle(categorySearchValues.getText()));
    }

}
