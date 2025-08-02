package com.java10x.geladeiraMagica.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java10x.geladeiraMagica.service.ChatGptService;

import reactor.core.publisher.Mono;

@RestController
public class RecipeController {

    private final ChatGptService service;

    public RecipeController(ChatGptService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<ResponseEntity<String>> generateRecipe(){
        return service.generateRecipe();

    }
}
