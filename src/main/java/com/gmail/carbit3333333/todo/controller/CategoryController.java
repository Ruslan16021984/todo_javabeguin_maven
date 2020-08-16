package com.gmail.carbit3333333.todo.controller;

import com.gmail.carbit3333333.todo.entity.Category;
import com.gmail.carbit3333333.todo.entity.Priority;
import com.gmail.carbit3333333.todo.repository.CategoryRepository;
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

    @GetMapping("/test")
    public List<Category> test() {
        List<Category> categoryList = repository.findAll();
        System.out.println(categoryList.toString());
        return categoryList;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
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
        Category category = null;
        try {
            category = repository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id +"not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);

    }

}
