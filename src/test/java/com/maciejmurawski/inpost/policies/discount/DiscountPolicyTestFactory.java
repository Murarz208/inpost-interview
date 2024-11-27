package com.maciejmurawski.inpost.policies.discount;

import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import com.maciejmurawski.inpost.policies.discount.entity.PolicyType;
import com.maciejmurawski.inpost.products.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class DiscountPolicyTestFactory {

    private DiscountPoliciesRepository discountPoliciesRepository;

    public DiscountPolicy createAmountDiscountForProduct(Product product, int productsCount, BigDecimal discountQuantity) {
        return createDiscountForProduct(product, productsCount, discountQuantity, PolicyType.AMOUNT);
    }

    public DiscountPolicy createPercentageDiscountForProduct(Product product, int productsCount, BigDecimal discountQuantity) {
        return createDiscountForProduct(product, productsCount, discountQuantity, PolicyType.PERCENTAGE);
    }

    private DiscountPolicy createDiscountForProduct(Product product, int productsCount, BigDecimal discountQuantity, PolicyType policyType) {
        return discountPoliciesRepository.save(
                DiscountPolicy.builder()
                        .type(policyType)
                        .discountQuantity(discountQuantity)
                        .productsCount(productsCount)
                        .productId(product.getId())
                        .build()
        );
    }

}
