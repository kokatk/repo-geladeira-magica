package com.java10x.geladeiraMagica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java10x.geladeiraMagica.model.FoodItem;
import com.java10x.geladeiraMagica.repository.FoodItemRepository;

@Service
public class FoodItemService {

    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    public FoodItem cadastrar(FoodItem foodItem){
        return repository.save(foodItem);
    }

    public FoodItem atualizar(FoodItem foodItem){
        return repository.save(foodItem);
    }

    public List<FoodItem> listarTodos(){
        return repository.findAll();
    }

    public Optional<FoodItem> listarPorId(Long id){
        return repository.findById(id);
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }

    

}   
