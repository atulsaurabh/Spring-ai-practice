package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.creative.engineering.chatclient.model.Chat;

import java.util.List;

@RestController
@RequestMapping("/memory/chat")
public class ChatWithMemoryController
{
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    ChatWithMemoryController(ChatClient.Builder builder, ChatMemoryRepository chatMemoryRepository)
    {
        chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .build();
          this.chatClient = builder
                  .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                  .build();
    }

    @PostMapping
    public String communicateWithHistory(@RequestBody Chat chat)
    {
        return this.chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, chat.getConversationId()))
                .system("You are ai teacher with knowledge of various subjects")
                .user(chat.getMessage())
                .call()
                .content();
    }
}
