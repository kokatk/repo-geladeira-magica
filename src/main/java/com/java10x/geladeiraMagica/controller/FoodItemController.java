package com.java10x.geladeiraMagica.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.java10x.geladeiraMagica.model.FoodItem;
import com.java10x.geladeiraMagica.repository.FoodItemRepository;
import com.java10x.geladeiraMagica.service.FoodItemService;

@RestController
public class FoodItemController {

    private final FoodItemService foodItemService;
    private final FoodItemRepository foodItemRepository;
    

    public FoodItemController(FoodItemService foodItemService, FoodItemRepository foodItemRepository) {
        this.foodItemService = foodItemService;
        this.foodItemRepository = foodItemRepository;
    }

    //LISTAR TODOS OS ITENS
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(foodItemRepository.findAll());
    }

    //ADICIONAR UM ITEM
    public ResponseEntity<?> adicionarItem(FoodItem foodItem) {
        foodItemRepository.save(foodItem);
        return ResponseEntity.ok("Item adicionado com sucesso!");
    }

    //REMOVER UM ITEM
    public ResponseEntity<?> removerItem(Long id) {
        if (foodItemRepository.existsById(id)) {
            foodItemRepository.deleteById(id);
            return ResponseEntity.ok("Item removido com sucesso!");
        } else {
            return ResponseEntity.status(Response.SC_NOT_FOUND).body("Item não encontrado!");
        }
    }

    //ATUALIZAR UM ITEM
    public ResponseEntity<?> atualizarItem(Long id, FoodItem foodItem) {
        if (foodItemRepository.existsById(id)) {
            foodItem.setId(id);
            foodItemRepository.save(foodItem);
            return ResponseEntity.ok("Item atualizado com sucesso!");
        } else {
            return ResponseEntity.status(Response.SC_NOT_FOUND).body("Item não encontrado!");
        }
    }
    
    //BUSCAR UM ITEM POR ID
    public ResponseEntity<?> buscarItemPorId(Long id) {
        return foodItemRepository.findById(id)
                .map(item -> ResponseEntity.ok(item))
                .orElse(ResponseEntity.status(Response.SC_NOT_FOUND).body("Item não encontrado!"));
    }
    
}
