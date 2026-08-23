package com.example.demo.controller;

import com.example.demo.model.Conversation;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.service.ChatService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("/chat")
    public String chatPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            return "redirect:/login";
        model.addAttribute("conversations", chatService.getConversationsForUser(userId));
        model.addAttribute("userId", userId);
        return "chat";
    }

    @GetMapping("/chat/{conversationId}")
    public String chatPageWithConversation(@PathVariable Long conversationId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            return "redirect:/login";
        model.addAttribute("conversations", chatService.getConversationsForUser(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("currentConversationId", conversationId);
        return "chat";
    }

    @GetMapping("/chat/new")
    public String newChatPage() {
        return "new_chat";
    }

    @PostMapping("/chat/new")
    public String startNewChat(@RequestParam String username, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            return "redirect:/login";

        User target = userService.findByUsername(username);
        if (target != null && !target.getId().equals(userId)) {
            Conversation conv = chatService.getOrCreatePrivateConversation(userId, target.getId());
            return "redirect:/chat/" + conv.getId();
        }
        return "redirect:/chat/new?error";
    }

    @GetMapping("/api/chat/{conversationId}")
    @ResponseBody
    public List<Message> getMessages(@PathVariable Long conversationId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            throw new RuntimeException("Not logged in");
        return chatService.getMessages(conversationId);
    }
}