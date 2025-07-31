package com.java10x.geladeiraMagica.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java10x.geladeiraMagica.model.FoodItem;
import com.java10x.geladeiraMagica.service.FoodItemService;

@RestController
public class FoodItemController {

    private FoodItemService service;



    //ADICIONAR UM ITEM
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarItem(@RequestBody FoodItem foodItem) {
        FoodItem salvo = service.cadastrar(foodItem);
        return ResponseEntity.ok().body(salvo);
    }

    //ATUALIZAR UM ITEM
    @PostMapping("atualizar/{id}")
    public ResponseEntity<FoodItem> atualizar(@PathVariable Long id, @RequestBody FoodItem foodItem) {
        return service.listarPorId(id)
        .map(itemExistente -> {
            foodItem.setId(itemExistente.getId());
            FoodItem atulizado = service.atualizar(foodItem);
            return ResponseEntity.ok(atulizado);
        })
        .orElse(ResponseEntity.notFound().build());
    }

       //LISTAR TODOS OS ITENS
    @GetMapping("/listar")
    public ResponseEntity<List<FoodItem>> listarTodos() {
        List<FoodItem> food = service.listarTodos();
        return ResponseEntity.ok(food);
    }

      //BUSCAR UM ITEM POR ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<Optional<FoodItem>> buscarItemPorId(@PathVariable Long id) {
        Optional<FoodItem> food = service.listarPorId(id);
        return ResponseEntity.ok(food);
    }


        //REMOVER UM ITEM
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
