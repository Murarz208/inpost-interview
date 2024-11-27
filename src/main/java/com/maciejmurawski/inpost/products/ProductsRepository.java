package com.maciejmurawski.inpost.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface ProductsRepository extends JpaRepository<Product, UUID> {

    default Product findProductById(UUID productId) {
        return findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
