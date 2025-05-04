package com.spring.repo;

import com.spring.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemReposiotry extends JpaRepository<Item ,Long> {

}
