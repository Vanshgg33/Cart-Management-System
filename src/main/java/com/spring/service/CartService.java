package com.spring.service;

import com.spring.repo.CartRepository;
import com.spring.repo.ItemReposiotry;
import com.spring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemReposiotry itemReposiotry;
}
