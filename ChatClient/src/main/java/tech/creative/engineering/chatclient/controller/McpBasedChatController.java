package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.creative.engineering.chatclient.model.StudentInfo;
import tech.creative.engineering.chatclient.model.StudentQuery;

@RestController
@RequestMapping("/mcp/chat")
public class McpBasedChatController
{

    private final ChatClient chatClient;

    public McpBasedChatController(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider)
    {
        this.chatClient = builder
                .defaultTools(toolCallbackProvider)
                .build();
    }

    @PostMapping
    public StudentInfo getStudentInfo(@RequestBody StudentQuery studentQuery)
    {
        return chatClient.prompt()
                .system("You are a helpful assistant.")
                .user("The enrollment number is " + studentQuery.getEnrollmentNumber())
                .call()
                .entity(StudentInfo.class);
    }
}
