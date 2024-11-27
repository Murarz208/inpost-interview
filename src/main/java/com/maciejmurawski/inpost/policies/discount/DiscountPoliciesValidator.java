package com.maciejmurawski.inpost.policies.discount;

import com.maciejmurawski.inpost.policies.discount.web.PolicyFormDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.maciejmurawski.inpost.policies.discount.entity.PolicyType.PERCENTAGE;
import static java.math.BigDecimal.ONE;

@Slf4j
@Service
@AllArgsConstructor
class DiscountPoliciesValidator {

    private final DiscountPoliciesRepository discountPoliciesRepository;

    void validateNewPolicy(UUID productId, PolicyFormDto policyDto) {
        if (discountPoliciesRepository.existsByProductIdAndProductsCountEquals(productId, policyDto.productsCount())) {
            throw new DiscountPolicyValidationException("Discount for product %s and product count %s already exists".formatted(productId, policyDto.productsCount()));
        }
        validatePolicyData(productId, policyDto);
    }

    private void validatePolicyData(UUID productId, PolicyFormDto policyDto) {
        if (policyDto.productsCount() <= 0) {
            throw new DiscountPolicyValidationException("Products count cannot be less than or equal to 0");
        }
        if (policyDto.discountQuantity().signum() <= 0) {
            throw new DiscountPolicyValidationException("Discount quantity cannot be less than or equal to 0");
        }
        if (PERCENTAGE.equals(policyDto.type()) && policyDto.discountQuantity().compareTo(ONE) > 0) {
            throw new DiscountPolicyValidationException("Discount quantity for percentage type cannot be more than 100");
        }
    }

    void validateExistingPolicy(UUID productId, UUID policyId, PolicyFormDto policyDto) {
        if (discountPoliciesRepository.existsByIdNotAndProductIdAndProductsCountEquals(policyId, productId, policyDto.productsCount())) {
            throw new DiscountPolicyValidationException("Discount for product %s and product count %s already exists".formatted(productId, policyDto.productsCount()));
        }
        validatePolicyData(productId, policyDto);
    }

}
