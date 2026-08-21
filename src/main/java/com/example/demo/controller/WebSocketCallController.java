package com.example.demo.controller;

import com.example.demo.model.Call;
import com.example.demo.service.CallService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketCallController {

    private final CallService callService;

    public WebSocketCallController(CallService callService) {
        this.callService = callService;
    }

    @MessageMapping("/call.initiate")
    @SendTo("/user/{calleeId}/queue/calls")
    public Call initiateCall(@Payload CallRequest request, Principal principal) {
        Long callerId = Long.parseLong(principal.getName());
        return callService.initiateCall(callerId, request.getCalleeId(), request.getType());
    }

    @MessageMapping("/call.answer")
    public void answerCall(@Payload CallAnswerRequest request) {
        callService.updateCallStatus(request.getCallId(), Call.CallStatus.ONGOING);
    }

    public static class CallRequest {
        private Long calleeId;
        private Call.CallType type;

        public Long getCalleeId() { return calleeId; }
        public void setCalleeId(Long calleeId) { this.calleeId = calleeId; }
        public Call.CallType getType() { return type; }
        public void setType(Call.CallType type) { this.type = type; }
    }

    public static class CallAnswerRequest {
        private Long callId;

        public Long getCallId() { return callId; }
        public void setCallId(Long callId) { this.callId = callId; }
    }
}