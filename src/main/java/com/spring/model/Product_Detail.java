package com.spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int prod_id;
    private String prod_name;
    private String prod_description;
    private int prod_price;
    private int prod_quantity;
    private String prod_image;



}