package com.spring.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Cart_DB")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String productName;
    int price;

}
