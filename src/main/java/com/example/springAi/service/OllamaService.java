package com.example.springAi.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {

    private final OllamaChatModel chatModel;

    @Autowired
    public OllamaService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * Call Ollama model with a simple text prompt and return the response.
     *
     * @param message the user message to send to the model
     * @return the model's response text
     */
    public String getResponse(String message) {
        Prompt prompt = new Prompt(message);
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }

    /**
     * Call Ollama model with a custom prompt and return the full ChatResponse.
     *
     * @param prompt the prompt to send
     * @return the ChatResponse containing metadata and output
     */
    public ChatResponse getChatResponse(Prompt prompt) {
        return chatModel.call(prompt);
    }
}
