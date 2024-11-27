package com.maciejmurawski.inpost.policies.discount.web;

import com.maciejmurawski.inpost.policies.discount.entity.PolicyType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record PolicyFormDto(long productsCount, PolicyType type, BigDecimal discountQuantity) {

}
