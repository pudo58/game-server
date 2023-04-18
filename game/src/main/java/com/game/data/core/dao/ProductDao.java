package com.game.data.core.dao;

import com.game.data.core.entity.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {
}
