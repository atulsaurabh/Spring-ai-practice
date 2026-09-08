package tech.creative.engineering.chatclient.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;
import tech.creative.engineering.chatclient.entity.Conversation;
import tech.creative.engineering.chatclient.entity.MessageType;

import java.util.List;

@Component
public class JpaChatMemoryRepository implements ChatMemoryRepository
{
    private ConversationRepository conversationRepository;

    public JpaChatMemoryRepository(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public @NonNull List<String> findConversationIds()
    {
        return conversationRepository.findAll().stream().map(Conversation::getConversationId).toList();
    }

    @Override
    public @NonNull List<Message> findByConversationId(String conversationId) {
        return conversationRepository.findByConversationId(conversationId)
                .stream()
                .map(this::getMessage).toList();
    }

    @Override
    public void saveAll(@NonNull String conversationId, List<Message> messages)
    {
       List<Conversation> conversations = conversationRepository.findByConversationId(conversationId);
       conversations.addAll(messages.stream().map(message -> createConversation(message,conversationId)).toList());
       conversationRepository.saveAll(conversations);
    }

    private Conversation createConversation(Message message, String conversationId)
    {
        return switch (message.getMessageType())
        {
            case USER -> new Conversation(conversationId, MessageType.USER, message.getText());
            case SYSTEM ->  new Conversation(conversationId, MessageType.SYSTEM, message.getText());
            case ASSISTANT ->  new Conversation(conversationId, MessageType.ASSISTANT, message.getText());
            default -> new Conversation(conversationId, MessageType.USER, message.getText());
        };
    }

    @Override
    public void deleteByConversationId(@NonNull String conversationId)
    {
       conversationRepository.deleteByConversationId(conversationId);
    }


    private Message getMessage(Conversation conversation)
    {
        return switch (conversation.getMessageType())
        {
            case USER -> new UserMessage(conversation.getContent());
            case SYSTEM ->  new SystemMessage(conversation.getContent());
            case ASSISTANT ->  new AssistantMessage(conversation.getContent());
            default -> new UserMessage(conversation.getContent());
        };
    }
}
