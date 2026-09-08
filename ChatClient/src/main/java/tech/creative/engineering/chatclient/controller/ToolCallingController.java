package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.creative.engineering.chatclient.tool.DateTimeTools;

@RestController
@RequestMapping("/tool/chat")
public class ToolCallingController
{
  private final ChatClient chatClient;

  public ToolCallingController(ChatClient.Builder builder)
  {
    this.chatClient = builder.build();
  }

  @PostMapping
  public String toolCall()
  {
      return chatClient.prompt()
              .system("You are a helpful assistant.")
              .tools(new DateTimeTools())
              .user("Please set the alarm for current time")
              .call()
              .content();
  }
}
