package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/media/chat")
public class MediaBasedChatController
{
    private final ChatClient chatClient;
    MediaBasedChatController(ChatClient.Builder builder)
    {
        chatClient = builder.build();
    }

    @PostMapping
    public String identifyAndExplainImage(@RequestParam(name = "image") MultipartFile image)
    {

        MimeType mimeType = MimeTypeUtils.parseMimeType(Objects.requireNonNull(image.getContentType()));
        UserMessage userMessage = UserMessage.builder()
                .text("Explain this image in around 1000 words.")
                .media()
                .build();

        return chatClient.prompt()
                .system("You are an AI that can identify, Analyse and explain images content.")
                .user(u -> u.text("Explain this image in around 1000 words.")
                        .media(new Media(mimeType, image.getResource())))
                .call().content();
    }

}
