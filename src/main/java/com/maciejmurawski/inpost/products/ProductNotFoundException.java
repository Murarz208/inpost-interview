package com.maciejmurawski.inpost.products;

import java.util.UUID;

class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID productId) {
        super("Can't find product with id %s".formatted(productId));
    }
}
