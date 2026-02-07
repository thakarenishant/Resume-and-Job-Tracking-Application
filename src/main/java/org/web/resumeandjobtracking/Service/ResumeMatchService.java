package org.web.resumeandjobtracking.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.web.resumeandjobtracking.DTO.EmbeddingResponse;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ResumeMatchService {

    private static final Logger logger = Logger.getLogger(ResumeMatchService.class.getName());
    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public ResumeMatchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com/v1beta").build();
    }

    /**
     * Compute resume-job match score (0–100)
     */
    public double getMatchScore(String resumeText, String jobDescription) {
        List<Double> resumeEmbedding = getEmbedding(resumeText);
        List<Double> jobEmbedding = getEmbedding(jobDescription);

        return cosineSimilarity(resumeEmbedding, jobEmbedding) * 100;
    }

    /**
     * Call Gemini Embeddings API
     */
    private List<Double> getEmbedding(String text) {
        // Validate input
        if (text == null || text.trim().isEmpty()) {
            throw new RuntimeException("Input text cannot be null or empty");
        }

        // Validate API key
        if (geminiApiKey == null || geminiApiKey.trim().isEmpty()) {
            throw new RuntimeException("Gemini API key is not configured");
        }

        // Correct request body structure for Gemini API
        Map<String, Object> requestBody = Map.of(
                "model", "models/text-embedding-004",
                "content", Map.of(
                        "parts", List.of(Map.of("text", text))
                )
        );

        try {
            logger.info("Calling Gemini API for text embedding");
            
            EmbeddingResponse response = webClient.post()
                    .uri("/models/text-embedding-004:embedContent")
                    .header("x-goog-api-key", geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(EmbeddingResponse.class)
                    .block();

            // Check response structure matching the actual Gemini API
            if (response == null) {
                throw new RuntimeException("Failed to get response from Gemini API - response is null");
            }
            
            if (response.getEmbedding() == null) {
                logger.warning("Gemini API returned null embedding. Full response: " + response.toString());
                throw new RuntimeException("Failed to get embedding from Gemini API - embedding is null");
            }
            
            if (response.getEmbedding().getValues() == null || response.getEmbedding().getValues().isEmpty()) {
                throw new RuntimeException("Failed to get embedding from Gemini API - embedding values are null or empty");
            }

            logger.info("Successfully retrieved embedding with " + response.getEmbedding().getValues().size() + " dimensions");
            return response.getEmbedding().getValues();

        } catch (WebClientResponseException e) {
            logger.severe("API call failed with status: " + e.getStatusCode() + ", body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Gemini API call failed: " + e.getMessage() + ". Status: " + e.getStatusCode(), e);
        } catch (Exception e) {
            logger.severe("Unexpected error during API call: " + e.getMessage());
            throw new RuntimeException("Failed to get embedding from Gemini API: " + e.getMessage(), e);
        }
    }

    /**
     * Cosine similarity between two vectors
     */
    private double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        if (vec1 == null || vec2 == null) {
            throw new RuntimeException("Cannot calculate cosine similarity with null vectors");
        }
        
        if (vec1.size() != vec2.size()) {
            throw new RuntimeException("Vector dimensions don't match: " + vec1.size() + " vs " + vec2.size());
        }
        
        double dot = 0.0, normA = 0.0, normB = 0.0;

        for (int i = 0; i < vec1.size(); i++) {
            dot += vec1.get(i) * vec2.get(i);
            normA += Math.pow(vec1.get(i), 2);
            normB += Math.pow(vec2.get(i), 2);
        }

        double denominator = Math.sqrt(normA) * Math.sqrt(normB);
        if (denominator == 0.0) {
            return 0.0; // Handle zero vectors
        }


        return dot / denominator;
    }
    private void dummy(){

    }
}