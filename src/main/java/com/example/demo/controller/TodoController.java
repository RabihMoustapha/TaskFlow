package com.example.demo.controller;

import com.example.demo.service.TodoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/tasks")
    public String tasksPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        
        model.addAttribute("tasks", todoService.getTasksForUser(userId));
        return "tasks";
    }

    @PostMapping("/tasks/add")
    public String addTask(HttpSession session, @RequestParam String title) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        
        if (title != null && !title.trim().isEmpty()) {
            todoService.addTask(userId, title.trim());
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/toggle/{id}")
    public String toggleTask(@PathVariable Long id, @RequestParam boolean completed) {
        todoService.toggleTask(id, completed);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        todoService.deleteTask(id);
        return "redirect:/tasks";
    }
}