package com.java10x.geladeiraMagica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java10x.geladeiraMagica.model.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {


}
