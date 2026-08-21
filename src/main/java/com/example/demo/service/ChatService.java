package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ChatService {

    private final ConversationRepository conversationRepo;
    private final ConversationParticipantRepository participantRepo;
    private final MessageRepository messageRepo;
    private final UserRepository userRepo;

    public ChatService(ConversationRepository conversationRepo,
                       ConversationParticipantRepository participantRepo,
                       MessageRepository messageRepo,
                       UserRepository userRepo) {
        this.conversationRepo = conversationRepo;
        this.participantRepo = participantRepo;
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public Conversation getOrCreatePrivateConversation(Long userId1, Long userId2) {
        return conversationRepo.findPrivateConversation(userId1, userId2)
                .orElseGet(() -> {
                    Conversation conv = new Conversation();
                    conv.setType(Conversation.ConversationType.PRIVATE);
                    conv.setCreatedBy(userRepo.findById(userId1).orElseThrow());
                    conv = conversationRepo.save(conv);

                    addParticipant(conv, userId1);
                    addParticipant(conv, userId2);
                    return conv;
                });
    }

    @Transactional
    public Conversation createGroupConversation(Long creatorId, String name, List<Long> participantIds) {
        Conversation conv = new Conversation();
        conv.setType(Conversation.ConversationType.GROUP);
        conv.setName(name);
        conv.setCreatedBy(userRepo.findById(creatorId).orElseThrow());
        conv = conversationRepo.save(conv);

        addParticipant(conv, creatorId);
        for (Long id : participantIds) {
            addParticipant(conv, id);
        }
        return conv;
    }

    @Transactional
    public Message sendMessage(Long conversationId, Long senderId, String content) {
        Conversation conv = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        User sender = userRepo.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!participantRepo.existsByConversationIdAndUserId(conversationId, senderId)) {
            throw new RuntimeException("User is not a participant");
        }

        Message msg = new Message();
        msg.setConversation(conv);
        msg.setSender(sender);
        msg.setContent(content);
        return messageRepo.save(msg);
    }

    public List<Conversation> getConversationsForUser(Long userId) {
        return conversationRepo.findConversationsByUserId(userId);
    }

    public List<Message> getMessages(Long conversationId) {
        return messageRepo.findByConversationIdOrderBySentAtAsc(conversationId);
    }

    private void addParticipant(Conversation conv, Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        ConversationParticipant participant = new ConversationParticipant();
        participant.setConversation(conv);
        participant.setUser(user);
        participantRepo.save(participant);
    }
}