package com.spring.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private int id;
    private String username;
    private String password;

@OneToOne
    private Cart cart;


}
