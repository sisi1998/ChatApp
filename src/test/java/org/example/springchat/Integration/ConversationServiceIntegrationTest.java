package org.example.springchat.Integration;

import org.example.springchat.service.ConversationService;
import org.example.springchat.entity.Conversation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ConversationServiceIntegrationTest {

    @Autowired
    private ConversationService service;



    @Test
    void realChatGptCall() throws IOException {
        String response = service.getChatGptResponse("vv", "What's the capital of France?");
        System.out.println("ChatGPT says: " + response);
        Assertions.assertNotNull(response);
    }
}
