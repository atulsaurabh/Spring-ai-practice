package tech.creative.engineering.chatclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.creative.engineering.chatclient.entity.Conversation;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>
{
    List<Conversation> findByConversationId(String conversationId);
    void deleteByConversationId(String conversationId);
}
