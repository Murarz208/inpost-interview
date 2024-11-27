package com.maciejmurawski.inpost.assertions;

import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import com.maciejmurawski.inpost.policies.discount.entity.PolicyType;
import org.assertj.core.api.AbstractObjectAssert;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class DiscountPolicyAssertions extends AbstractObjectAssert<DiscountPolicyAssertions, DiscountPolicy> {

    public DiscountPolicyAssertions(DiscountPolicy actual) {
        super(actual, DiscountPolicyAssertions.class);
    }

    public static DiscountPolicyAssertions assertThat(DiscountPolicy actual) {
        return new DiscountPolicyAssertions(actual);
    }

    public DiscountPolicyAssertions hasNonNullId() {
        isNotNull();
        if (actual.getId() == null) {
            failWithMessage("Expected id to be not null but was null");
        }
        return this;
    }

    public DiscountPolicyAssertions hasProductId(UUID expectedProductId) {
        isNotNull();
        if (!Objects.equals(expectedProductId, actual.getProductId())) {
            failWithMessage("Expected productId to be <%s> but was <%s>", expectedProductId, actual.getProductId());
        }
        return this;
    }

    public DiscountPolicyAssertions hasProductsCount(long expectedProductsCount) {
        isNotNull();
        if (!Objects.equals(expectedProductsCount, actual.getProductsCount())) {
            failWithMessage("Expected productsCount to be <%s> but was <%s>", expectedProductsCount, actual.getProductsCount());
        }
        return this;
    }

    public DiscountPolicyAssertions hasDiscountQuantity(BigDecimal expectedDiscountQuantity) {
        isNotNull();
        if (!Objects.equals(expectedDiscountQuantity, actual.getDiscountQuantity())) {
            failWithMessage("Expected discountQuantity to be <%s> but was <%s>", expectedDiscountQuantity, actual.getDiscountQuantity());
        }
        return this;
    }

    public DiscountPolicyAssertions hasType(PolicyType expectedType) {
        isNotNull();
        if (!Objects.equals(expectedType, actual.getType())) {
            failWithMessage("Expected type to be <%s> but was <%s>", expectedType, actual.getType());
        }
        return this;
    }

}
