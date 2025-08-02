package com.java10x.geladeiraMagica.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private String apiKey = System.getenv("API_KEY");

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(){
        String prompt = "Agora você é um chefe de cozinha e vai me sugerir receitas com base nos ingredientes que vou te passar";
        Map<String, Object> requestBody = Map.of(
            "model", "gpt-4.1",
            "messages", List.of(
               Map.of("role", "system", "content", "você é um assistente que cria receitas"),
               Map.of("role", "user", "content", prompt)
            )
        );
        return
    }
}


/*curl https://api.openai.com/v1/responses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -d '{
    "model": "gpt-4.1",
    "input": "Tell me a three sentence bedtime story about a unicorn."
  }'
 */