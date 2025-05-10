package com.spring.repo;

import com.spring.model.Item;
import com.spring.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemReposiotry extends JpaRepository<Item ,Long> {
    // Find item by productName
    Item findByProductName(String productName);

    // Delete item by productName
    @Modifying
    @Transactional
    @Query("DELETE FROM Item i WHERE i.productName = :productName")
    void deleteByProductName(@Param("productName") String productName);
}
