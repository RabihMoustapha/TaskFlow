package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketChatController {

    private final ChatService chatService;

    public WebSocketChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/conversation/{conversationId}")
    public Message sendMessage(@Payload MessageRequest request, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return chatService.sendMessage(request.getConversationId(), userId, request.getContent());
    }

    public static class MessageRequest {
        private Long conversationId;
        private String content;

        public Long getConversationId() { return conversationId; }
        public void setConversationId(Long conversationId) { this.conversationId = conversationId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}