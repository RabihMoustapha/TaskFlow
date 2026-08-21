package com.example.demo.repository;

import com.example.demo.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c WHERE c.type = 'PRIVATE' AND " +
           "EXISTS (SELECT p1 FROM ConversationParticipant p1 WHERE p1.conversation = c AND p1.user.id = :user1) AND " +
           "EXISTS (SELECT p2 FROM ConversationParticipant p2 WHERE p2.conversation = c AND p2.user.id = :user2)")
    Optional<Conversation> findPrivateConversation(@Param("user1") Long user1, @Param("user2") Long user2);

    @Query("SELECT c FROM Conversation c JOIN c.participants p WHERE p.user.id = :userId")
    List<Conversation> findConversationsByUserId(@Param("userId") Long userId);
}