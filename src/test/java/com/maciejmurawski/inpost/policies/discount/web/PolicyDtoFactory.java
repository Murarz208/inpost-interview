package com.maciejmurawski.inpost.policies.discount.web;

import com.maciejmurawski.inpost.policies.discount.entity.PolicyType;

import java.math.BigDecimal;

public class PolicyDtoFactory {
    public static PolicyFormDto createFormDto() {
        return PolicyFormDto.builder()
                .productsCount(100L)
                .type(PolicyType.AMOUNT)
                .discountQuantity(BigDecimal.ONE)
                .build();
    }
}
