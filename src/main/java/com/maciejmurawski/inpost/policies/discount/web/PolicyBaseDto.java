package com.maciejmurawski.inpost.policies.discount.web;

import com.maciejmurawski.inpost.policies.discount.entity.PolicyType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PolicyBaseDto(@NotNull UUID id, @NotNull UUID productId, long productsCount,
                            @NotNull BigDecimal discountQuantity, @NotNull PolicyType type) {

}
