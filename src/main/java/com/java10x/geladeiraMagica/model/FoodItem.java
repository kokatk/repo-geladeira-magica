package com.java10x.geladeiraMagica.model;

import java.time.LocalDate;



import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "food_item")
public class FoodItem {

    private Long id;

    private String nome;

    private String categoria;

    private Integer quantidade;

    private LocalDate dataValidade;
    
    
}
