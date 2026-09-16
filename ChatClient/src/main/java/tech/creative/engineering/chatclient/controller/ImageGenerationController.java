package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.creative.engineering.chatclient.model.ImageQuery;

import java.util.Base64;
import java.util.Objects;

@RestController
@RequestMapping("/llm/image-generation")
public class ImageGenerationController
{

    private ImageModel imageModel;

    public ImageGenerationController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte [] generateImage(@RequestBody ImageQuery imageQuery)
    {
        var options = OpenAiImageOptions
                .builder()
                .width(1024)
                .height(1024)
                .model("gpt-image-1")
                .build();
        ImagePrompt prompt = new ImagePrompt(imageQuery.getImagePrompt(), options);
        var imageResponse = imageModel.call(prompt);
        return Base64.getDecoder().decode(Objects.requireNonNull(imageResponse.getResult()).getOutput().getB64Json());
    }
}
