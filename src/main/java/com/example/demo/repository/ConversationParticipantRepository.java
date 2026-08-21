package com.example.demo.repository;

import com.example.demo.model.ConversationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, Long> {
    List<ConversationParticipant> findByConversationId(Long conversationId);
    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);
}