package com.food.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Primary Key with AUTO_INCREMENT ✅

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    private Food food;
    
    private List<String> ingredients;
    
    private Long totalPrice; 
    
    private String productName;
    private int quantity;
    private double price;
}
