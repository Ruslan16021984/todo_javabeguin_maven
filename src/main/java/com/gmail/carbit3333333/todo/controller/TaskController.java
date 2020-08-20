package com.gmail.carbit3333333.todo.controller;

import com.gmail.carbit3333333.todo.entity.Task;
import com.gmail.carbit3333333.todo.repository.TaskRepository;
import com.gmail.carbit3333333.todo.search.TaskSearchValue;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    @GetMapping("/all")
    public List<Task> findAll() {
        System.out.println("Task:findAll----------------------");
        return repository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {
        System.out.println("CategoryController:add----------------------");
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(repository.save(task));
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {
        System.out.println("Task:update----------------------");
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("redundant param: id ", HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(repository.save(task));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Task> deleteByid(@PathVariable Long id) {
        System.out.println("Task:delete/{id}----------------------");
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found ", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        System.out.println("CategoryController:{id}----------------------");
        Task task = null;
        try {
            task = repository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);

    }

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValue taskSearchValue) {

        String text = taskSearchValue.getTitle() != null ? taskSearchValue.getTitle() : null;

        Integer completed = taskSearchValue.getCompleted() != null ? taskSearchValue.getCompleted() : null;

        Long priorityId = taskSearchValue.getPriorityId() != null ? taskSearchValue.getPriorityId() : null;
        Long categoryId = taskSearchValue.getCategoryId() != null ? taskSearchValue.getCategoryId() : null;

        String sortColumn = taskSearchValue.getSortColumn() !=null ? taskSearchValue.getSortColumn() : null;
        String sortDirection = taskSearchValue.getSortDirection() !=null ? taskSearchValue.getSortDirection() : null;

        Integer pageNumber = taskSearchValue.getPageNumber() !=null ? taskSearchValue.getPageNumber() : null;
        Integer pageSize = taskSearchValue.getPageSize() !=null ? taskSearchValue.getPageSize() : null;

        Sort.Direction direction = sortDirection ==null || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        //обьект сортировки
        Sort sort = Sort.by(direction, sortColumn);
        //обьект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page result = repository.findByParams(text, completed, priorityId, categoryId, pageRequest);
        return ResponseEntity.ok(result);
    }

}
