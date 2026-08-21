package com.example.demo.repository;

import com.example.demo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query("SELECT s FROM Status s WHERE s.expiresAt > CURRENT_TIMESTAMP ORDER BY s.createdAt DESC")
    List<Status> findActiveStatuses();

    List<Status> findByUserIdOrderByCreatedAtDesc(Long userId);
}