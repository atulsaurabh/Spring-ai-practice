package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.creative.engineering.chatclient.model.Movie;
import tech.creative.engineering.chatclient.model.MovieQuery;
import tech.creative.engineering.chatclient.service.VectorDbService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rag/movie-description")
public class MovieDescription {


    private final Resource resource;
    private final VectorDbService vectorDbService;
    private final Resource promptResource;
    private final ChatClient chatClient;

    public MovieDescription(ChatClient.Builder builder,@Value("classpath:/movie/movies.csv")Resource resource,
                            VectorDbService vectorDbService,
                            @Value("classpath:/templates/rag-prompt-template.st") Resource promptResource) {
        this.chatClient = builder.build();
        this.resource = resource;
        this.vectorDbService = vectorDbService;
        this.promptResource = promptResource;
    }


    @PostMapping("/init")
    public ResponseEntity<?> initVectorDB() {
        vectorDbService.loadCSV(resource);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<List<Movie>> queryAMovie(@RequestBody MovieQuery  query)
    {
        List<Document> documents = vectorDbService.getDocuments(query.getQuery());
        List<String> contentList=documents.stream().map(Document::getText).toList();

        PromptTemplate promptTemplate = new PromptTemplate(promptResource);
        Prompt prompt = promptTemplate.create(Map.of("input", query.getQuery(), "documents", String.join("\n", contentList)));
        return ResponseEntity.ok(chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<List<Movie>>() {
        }));

    }



}
