package com.example.demo.controller;

import com.example.demo.service.StatusService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/statuses")
    public String statusesPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        model.addAttribute("activeStatuses", statusService.getActiveStatuses());
        return "statuses";
    }

    @PostMapping("/statuses")
    public String createStatus(HttpSession session,
                               @RequestParam String mediaUrl,
                               @RequestParam(required = false) String caption) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        statusService.createStatus(userId, mediaUrl, caption);
        return "redirect:/statuses";
    }
}