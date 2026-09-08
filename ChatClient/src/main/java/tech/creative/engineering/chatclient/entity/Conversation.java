package tech.creative.engineering.chatclient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.time.LocalDateTime;

@Entity
@Table(name = "CONVERSATION")
@Setter
@Getter
@NoArgsConstructor
public class Conversation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNICATION_ID")
    private Long communicationId;

    @CollectionTable(name = "CONVERSATION_ID")
    private String conversationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "MESSAGE_TYPE")
    private MessageType messageType;

    @Column(name = "CONTENT", length = 5000)
    private String content;

    private LocalDateTime createdAt;

    public Conversation(String conversationId, MessageType messageType, String content) {
        this.conversationId = conversationId;
        this.messageType = messageType;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
