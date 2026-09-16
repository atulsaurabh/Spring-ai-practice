package tech.creative.engineering.chatclient.controller;

import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import tech.creative.engineering.chatclient.model.ImageQuery;

@RestController
@RequestMapping("/llm/audio")
public class AudioController
{
    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;

    public AudioController(OpenAiAudioSpeechModel openAiAudioSpeechModel) {
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;
    }

    @PostMapping(produces = "audio/mpeg")
    public ResponseEntity<byte []> translate(@RequestBody ImageQuery audio) {

        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .responseFormat(OpenAiAudioSpeechOptions.AudioResponseFormat.MP3)
                .speed(1.0)
                .voice(OpenAiAudioSpeechOptions.Voice.ALLOY)
                .model(OpenAiAudioSpeechOptions.DEFAULT_SPEECH_MODEL)
                .build();

        TextToSpeechPrompt prompt = new TextToSpeechPrompt(audio.getImagePrompt(), options);

        return ResponseEntity.ok(openAiAudioSpeechModel.call(prompt).getResult().getOutput());
    }
}
