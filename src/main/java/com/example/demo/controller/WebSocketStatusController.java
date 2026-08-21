package com.example.demo.controller;

import com.example.demo.model.Status;
import com.example.demo.service.StatusService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketStatusController {

    private final StatusService statusService;

    public WebSocketStatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @MessageMapping("/status.post")
    @SendTo("/topic/statuses")
    public Status postStatus(@Payload StatusRequest request, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return statusService.createStatus(userId, request.getMediaUrl(), request.getCaption());
    }

    public static class StatusRequest {
        private String mediaUrl;
        private String caption;

        public String getMediaUrl() { return mediaUrl; }
        public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
        public String getCaption() { return caption; }
        public void setCaption(String caption) { this.caption = caption; }
    }
}