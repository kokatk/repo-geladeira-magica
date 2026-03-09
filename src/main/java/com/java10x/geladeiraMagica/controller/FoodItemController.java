package com.java10x.geladeiraMagica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.java10x.geladeiraMagica.model.FoodItem;
import com.java10x.geladeiraMagica.service.FoodItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/food")
@Tag(name = "Itens da Geladeira", description = "Endpoints para gerenciar itens na geladeira")
public class FoodItemController {

    private final FoodItemService service;

    public FoodItemController(FoodItemService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um item na geladeira")
    @PostMapping("/cadastrar")
    public ResponseEntity<FoodItem> cadastrarItem(@RequestBody FoodItem foodItem) {
        FoodItem salvo = service.cadastrar(foodItem);
        return ResponseEntity.ok().body(salvo);
    }

    @Operation(summary = "Atualizar um item na geladeira")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FoodItem> atualizar(@PathVariable Long id, @RequestBody FoodItem foodItem) {
        return service.listarPorId(id)
        .map(itemExistente -> {
            foodItem.setId(itemExistente.getId());
            FoodItem atualizado = service.atualizar(foodItem);
            return ResponseEntity.ok(atualizado);
        })
        .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos os itens da geladeira")
    @GetMapping("/listar")
    public ResponseEntity<List<FoodItem>> listarTodos() {
        List<FoodItem> food = service.listarTodos();
        return ResponseEntity.ok(food);
    }

    @Operation(summary = "Listar um item por ID")
    @GetMapping("/listar/{id}")
    public ResponseEntity<FoodItem> buscarItemPorId(@PathVariable Long id) {
        Optional<FoodItem> food = service.listarPorId(id);
        return food.map(item -> ResponseEntity.ok(item))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar um item da geladeira")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        return service.listarPorId(id)
            .map(item -> {
                service.deletar(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}