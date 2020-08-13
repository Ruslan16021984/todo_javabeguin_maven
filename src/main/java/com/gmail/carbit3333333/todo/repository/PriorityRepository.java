package com.gmail.carbit3333333.todo.repository;

import com.gmail.carbit3333333.todo.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
