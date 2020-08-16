package com.gmail.carbit3333333.todo.repository;

import com.gmail.carbit3333333.todo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
