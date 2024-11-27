package com.maciejmurawski.inpost.products;

import com.maciejmurawski.inpost.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ProductsTestFactory {

    private ProductsRepository productsRepository;

    public Product createNewProduct(int amount, BigDecimal price) {
        return productsRepository.save(
                Product.builder()
                        .name("test")
                        .amount(amount)
                        .price(MonetaryAmount.pln(price))
                        .build()
        );
    }

    public Product createNewProduct() {
        return productsRepository.save(
                Product.builder()
                        .name("test")
                        .amount(100)
                        .price(MonetaryAmount.pln(BigDecimal.valueOf(100)))
                        .build()
        );
    }

}