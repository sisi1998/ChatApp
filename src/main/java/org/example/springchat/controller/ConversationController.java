package org.example.springchat.controller;  // Adjust the package to match your structure

import org.example.springchat.dto.PromptRequest;  // Correct?
import org.example.springchat.dto.PromptResponse;
import org.example.springchat.entity.Conversation; // Correct? // Correct import path for PromptResponse
import org.example.springchat.service.ConversationService;  // Correct import path for ConversationService
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConversationController {

    @Autowired
    private ConversationService service;

    @PostMapping("/prompts")
    @Operation(summary = "Ask a question to ChatGPT and save the conversation")
    public ResponseEntity<PromptResponse> askChatGpt(@RequestBody PromptRequest request) throws IOException {
        String answer = service.getChatGptResponse(request.getOpenAiToken(), request.getQuestion());
        service.save(request.getQuestion(), answer);
        return ResponseEntity.ok(new PromptResponse(answer));
    }

    @GetMapping("/conversations")
    @Operation(summary = "Get all stored conversations")
    public ResponseEntity<List<Conversation>> getAllConversations() {
        return ResponseEntity.ok(service.findAll());
    }
}
