package com.example.demo.service;

import com.example.demo.model.TodoItem;
import com.example.demo.model.User;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public List<TodoItem> getTasksForUser(Long userId) {
        return todoRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public TodoItem addTask(Long userId, String title) {
        User user = userRepository.findById(userId).orElseThrow();
        TodoItem task = new TodoItem();
        task.setUser(user);
        task.setTitle(title);
        return todoRepository.save(task);
    }

    public TodoItem toggleTask(Long taskId, boolean completed) {
        TodoItem task = todoRepository.findById(taskId).orElseThrow();
        task.setCompleted(completed);
        return todoRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        todoRepository.deleteById(taskId);
    }
}