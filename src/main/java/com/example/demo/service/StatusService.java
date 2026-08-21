package com.example.demo.service;

import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepo;
    private final UserRepository userRepo;

    public StatusService(StatusRepository statusRepo, UserRepository userRepo) {
        this.statusRepo = statusRepo;
        this.userRepo = userRepo;
    }

    public Status createStatus(Long userId, String mediaUrl, String caption) {
        User user = userRepo.findById(userId).orElseThrow();
        Status status = new Status();
        status.setUser(user);
        status.setMediaUrl(mediaUrl);
        status.setCaption(caption);
        return statusRepo.save(status);
    }

    public List<Status> getActiveStatuses() {
        return statusRepo.findActiveStatuses();
    }
}