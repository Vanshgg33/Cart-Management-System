package com.spring.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String productName;
    int price;

}
