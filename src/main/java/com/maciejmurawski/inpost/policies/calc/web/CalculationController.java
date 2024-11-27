package com.maciejmurawski.inpost.policies.calc.web;

import com.maciejmurawski.inpost.policies.calc.DiscountCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{productId}/policies/calculate")
class CalculationController {

    private final DiscountCalculator discountCalculator;

    @GetMapping
    CalculationResultsDto calculateDiscount(@PathVariable UUID productId, @RequestParam("amount") long amount) {
        return discountCalculator.calculateDiscountedPrice(productId, amount);
    }

}
