package com.java10x.geladeiraMagica.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private final String apiKey;

    public ChatGptService(WebClient webClient, @Value("${API_KEY}") String apiKey) {
        this.webClient = webClient;
        this.apiKey = apiKey;
    }

    public Mono<String> generateRecipe(List<String> ingredients){
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return Mono.just("Erro: API_KEY não configurada. Configure a variável de ambiente API_KEY.");
        }
        
        String prompt = "Você é um chefe de cozinha experiente. Crie uma receita deliciosa usando os seguintes ingredientes: "
            + String.join(", ", ingredients) + ". Inclua modo de preparo detalhado.";
            
        Map<String, Object> requestBody = Map.of(
            "model", "gpt-3.5-turbo",
            "messages", List.of(
                Map.of("role", "system", "content", "Você é um assistente culinário que cria receitas criativas e detalhadas."),
                Map.of("role", "user", "content", prompt)
            ),
            "max_tokens", 500,
            "temperature", 0.7
        );
        
        return webClient.post()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> {
                var choices = (List<Map<String, Object>>) response.get("choices");
                if (choices != null && !choices.isEmpty()){
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return message.get("content").toString();
                }
                return "Nenhuma receita foi gerada.";
            })
            .onErrorReturn("Erro ao gerar receita: Verifique sua conexão e chave da API.");
    }
}