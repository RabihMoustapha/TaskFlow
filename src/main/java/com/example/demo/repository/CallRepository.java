package com.example.demo.repository;

import com.example.demo.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CallRepository extends JpaRepository<Call, Long> {
    List<Call> findByCallerIdOrCalleeIdOrderByStartedAtDesc(Long callerId, Long calleeId);
}