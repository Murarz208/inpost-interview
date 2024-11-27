package com.maciejmurawski.inpost.policies.calc.web;

import com.maciejmurawski.inpost.money.MonetaryAmount;
import lombok.Builder;

@Builder
public record CalculationResultsDto(MonetaryAmount discountedPrice,
                                    MonetaryAmount basePrice,
                                    long productsCountBefore,
                                    long productsCountAfter) {
}
