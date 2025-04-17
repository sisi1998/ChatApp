package org.example.springchat.service;

import org.example.springchat.entity.Conversation;
import org.example.springchat.repository.ConversationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository repository;

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;


    public Conversation save(String question, String response) {
        Conversation conv = new Conversation();
        conv.setUserQuestion(question);
        conv.setGptResponse(response);
        return repository.save(conv);
    }

    public List<Conversation> findAll() {
        return repository.findAll();
    }

    public String getChatGptResponse(String token, String question) throws IOException {
        // Use openAiApiKey instead of token
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String bodyJson = "{\"model\":\"gpt-4.1-nano\",\"messages\":[{\"role\":\"user\",\"content\":\"" + question + "\"}]}";

        RequestBody body = RequestBody.create(mediaType, bodyJson);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + openAiApiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        // Rest of the method remains the same
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            return root.path("choices").get(0).path("message").path("content").asText();
        }
    }



}
