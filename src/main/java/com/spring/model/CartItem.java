package com.spring.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)  // Cascade persist
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Item item;

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +

                ", item=" + item +
                '}';
    }
}
