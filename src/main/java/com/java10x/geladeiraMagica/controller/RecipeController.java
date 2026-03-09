package com.java10x.geladeiraMagica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.java10x.geladeiraMagica.service.ChatGptService;
import com.java10x.geladeiraMagica.service.FoodItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.java10x.geladeiraMagica.model.FoodItem;

import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Receitas", description = "Endpoints para gerar receitas com base em ingredientes")
public class RecipeController {

    private final ChatGptService chatGptService;
    private final FoodItemService foodItemService;

    public RecipeController(ChatGptService chatGptService, FoodItemService foodItemService) {
        this.chatGptService = chatGptService;
        this.foodItemService = foodItemService;
    }

    @Operation(summary = "Gerar receita com ingredientes da geladeira")
    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe(){
        List<FoodItem> ingredientes = foodItemService.listarTodos();
        
        if (ingredientes.isEmpty()) {
            return Mono.just(ResponseEntity.badRequest()
                .body("Sua geladeira está vazia! Adicione alguns ingredientes primeiro usando /food/cadastrar"));
        }
        
        List<String> nomes = ingredientes.stream()
            .map(FoodItem::getNome)
            .toList();
            
        return chatGptService.generateRecipe(nomes)
            .map(recipe -> ResponseEntity.ok(recipe))
            .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Gerar receita com ingredientes manuais (query)")
    @GetMapping("/generate-manual")
    public Mono<ResponseEntity<String>> generateRecipeManual(@RequestParam List<String> ingredients){
        if (ingredients == null || ingredients.isEmpty()) {
            return Mono.just(ResponseEntity.badRequest()
                .body("Por favor, forneça pelo menos um ingrediente. Exemplo: /generate-manual?ingredients=frango&ingredients=batata"));
        }
        
        return chatGptService.generateRecipe(ingredients)
            .map(recipe -> ResponseEntity.ok(recipe))
            .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Gerar receita com ingredientes manuais (JSON)")
    @PostMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipePost(@RequestBody IngredientsRequest request){
        if (request.getIngredients() == null || request.getIngredients().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest()
                .body("Por favor, forneça pelo menos um ingrediente no formato: {\"ingredients\": [\"frango\", \"batata\"]}"));
        }
        
        return chatGptService.generateRecipe(request.getIngredients())
            .map(recipe -> ResponseEntity.ok(recipe))
            .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    // Classe para receber o JSON no POST
    public static class IngredientsRequest {
        private List<String> ingredients;

        public List<String> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
        }
    }
}