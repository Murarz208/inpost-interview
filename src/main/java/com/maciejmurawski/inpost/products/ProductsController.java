package com.maciejmurawski.inpost.products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
class ProductsController {

    private final ProductsQueryService productsQueryService;
    private final ProductsMapper productsMapper;

    @GetMapping
    Page<ProductBaseDto> retrieveAll(Pageable page) {
        return productsQueryService.retrieveAll(page).map(productsMapper::toProductBaseDto);
    }

    @GetMapping("{productId}")
    ProductBaseDto retrieveOne(@PathVariable UUID productId) {
        return productsMapper.toProductBaseDto(productsQueryService.retrieveOne(productId));
    }

}
