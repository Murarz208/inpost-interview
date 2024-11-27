package com.maciejmurawski.inpost.products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsQueryService {

    private final ProductsRepository productsRepository;

    @Transactional(readOnly = true)
    public Page<Product> retrieveAll(Pageable page) {
        return productsRepository.findAll(page);
    }

    @Transactional(readOnly = true)
    public Product retrieveOne(UUID productId) {
        return productsRepository.findProductById(productId);
    }
}
