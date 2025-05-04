package com.spring.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Item {
    @Id
    int prdouct_id;
    String productName;
    int price;

}
