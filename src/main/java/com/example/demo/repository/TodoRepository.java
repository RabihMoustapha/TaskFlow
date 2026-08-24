package com.example.demo.repository;

import com.example.demo.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByUserIdOrderByCreatedAtDesc(Long userId);
}