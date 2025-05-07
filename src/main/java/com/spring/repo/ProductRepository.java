package com.spring.repo;

import com.spring.model.Product_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product_Detail, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Product_Detail p SET p.prod_quantity = :quantity WHERE p.id = :id")
    void updateProductQuantityById(@Param("id") Long id, @Param("quantity") int quantity);
}
