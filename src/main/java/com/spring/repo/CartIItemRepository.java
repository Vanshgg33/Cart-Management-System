package com.spring.repo;

import com.spring.model.CartItem;
import com.spring.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartIItemRepository extends JpaRepository<CartItem, Long > {
    void deleteCartItemByItem(Item item);
}
