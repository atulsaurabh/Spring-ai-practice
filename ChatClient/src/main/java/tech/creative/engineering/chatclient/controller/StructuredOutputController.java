package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.creative.engineering.chatclient.model.Movie;

import java.util.List;

@RestController
@RequestMapping("/structured/chat")
public class StructuredOutputController
{
    private final ChatClient chatClient;
    StructuredOutputController(ChatClient.Builder builder)
    {
        this.chatClient = builder.build();
    }

    @GetMapping
    public List<Movie> getMovies(@RequestParam(name = "hero") String hero)
    {
        return chatClient.prompt()
                .system("You are a movie recommendation system. You will be given a hero name and you will return a list of movies that the hero has appeared in.")
                .user("Hero: " + hero)
                .call()
                .entity(new ParameterizedTypeReference<List<Movie>>() {
                });
    }
}
