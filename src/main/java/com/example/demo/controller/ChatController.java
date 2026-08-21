package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.ChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public String chatPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        model.addAttribute("conversations", chatService.getConversationsForUser(userId));
        model.addAttribute("userId", userId);
        return "chat";
    }

    @GetMapping("/chat/{conversationId}")
    @ResponseBody
    public List<Message> getMessages(@PathVariable Long conversationId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) throw new RuntimeException("Not logged in");
        // optionally check participant
        return chatService.getMessages(conversationId);
    }
}