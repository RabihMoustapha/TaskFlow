package com.example.demo.service;

import com.example.demo.model.Call;
import com.example.demo.model.User;
import com.example.demo.repository.CallRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CallService {

    private final CallRepository callRepo;
    private final UserRepository userRepo;

    public CallService(CallRepository callRepo, UserRepository userRepo) {
        this.callRepo = callRepo;
        this.userRepo = userRepo;
    }

    public Call initiateCall(Long callerId, Long calleeId, Call.CallType type) {
        User caller = userRepo.findById(callerId).orElseThrow();
        User callee = userRepo.findById(calleeId).orElseThrow();
        Call call = new Call();
        call.setCaller(caller);
        call.setCallee(callee);
        call.setType(type);
        return callRepo.save(call);
    }

    public void updateCallStatus(Long callId, Call.CallStatus status) {
        Call call = callRepo.findById(callId).orElseThrow();
        call.setStatus(status);
        if (status == Call.CallStatus.ENDED || status == Call.CallStatus.MISSED) {
            call.setEndedAt(LocalDateTime.now());
        }
        callRepo.save(call);
    }

    public List<Call> getCallHistory(Long userId) {
        return callRepo.findByCallerIdOrCalleeIdOrderByStartedAtDesc(userId, userId);
    }
}