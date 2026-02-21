package com.example.springAi.controller;

import com.example.springAi.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ollama")
public class OllamaController {

    private final OllamaService ollamaService;

    @Value("${spring.ai.ollama.chat.model:llama3.2}")
    private String modelName;

    @Autowired
    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    /**
     * POST endpoint to send a message to the Ollama model.
     *
     * Example request:
     * POST /api/ollama/chat
     * Body: {"message": "What is the capital of France?"}
     *
     * @param request Map containing the "message" key
     * @return ResponseEntity with the model's response and model name
     */
    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Message is required");
            return ResponseEntity.badRequest().body(error);
        }

        String response = ollamaService.getResponse(message);

        Map<String, String> result = new HashMap<>();
        result.put("response", response);
        result.put("model", modelName);
        return ResponseEntity.ok(result);
    }

    /**
     * GET endpoint to get the current model name.
     *
     * @return the model name
     */
    @GetMapping("/model")
    public ResponseEntity<Map<String, String>> getModel() {
        Map<String, String> result = new HashMap<>();
        result.put("model", modelName);
        return ResponseEntity.ok(result);
    }

    /**
     * GET endpoint for simple health check.
     *
     * @return status message
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "Ollama API is running");
        status.put("model", modelName);
        return ResponseEntity.ok(status);
    }

    /**
     * GET endpoint to fetch top book recommendations by genre.
     * Returns JSON-formatted book data with title, author, and summary.
     *
     * @param genre the book genre (optional, defaults to "fiction")
     * @return JSON array of book recommendations
     */
    @GetMapping("/books")
    public ResponseEntity<Map<String, Object>> getBookRecommendations(
            @RequestParam(defaultValue = "fiction") String genre) {

        String prompt = String.format(
            "Recommend 6 top %s books. Return ONLY a JSON array with this exact format: " +
            "[{\"title\": \"Book Title\", \"author\": \"Author Name\", \"summary\": \"Brief 2-3 sentence summary\"}, ...]. " +
            "No markdown, no explanation, just valid JSON.", genre);

        String response = ollamaService.getResponse(prompt);

        Map<String, Object> result = new HashMap<>();
        result.put("genre", genre);
        result.put("model", modelName);

        // Parse the response - try to extract JSON array
        try {
            // Clean up the response to extract JSON
            String cleanJson = response.trim();
            if (cleanJson.startsWith("```json")) {
                cleanJson = cleanJson.substring(7);
            } else if (cleanJson.startsWith("```")) {
                cleanJson = cleanJson.substring(3);
            }
            if (cleanJson.endsWith("```")) {
                cleanJson = cleanJson.substring(0, cleanJson.length() - 3);
            }
            cleanJson = cleanJson.trim();

            result.put("books", cleanJson);
        } catch (Exception e) {
            result.put("books", "[]");
            result.put("error", "Failed to parse response");
        }

        return ResponseEntity.ok(result);
    }
}
