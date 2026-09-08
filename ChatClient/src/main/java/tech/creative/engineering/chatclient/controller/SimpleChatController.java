package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class SimpleChatController
{
    private final ChatClient chatClient;
    SimpleChatController(ChatClient.Builder builder)
    {
        this.chatClient = builder.build();
    }

    @GetMapping
    public String chat(@RequestParam(name = "hero") String hero)
    {
        return chatClient.prompt()
                .system("You are expert in Hindi movie.")
                .user("Suggest a good movie of hero " + hero)
                .call()
                .content();
    }
}
