package com.maciejmurawski.inpost.policies.discount;

import com.maciejmurawski.inpost.AbstractTest;
import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import com.maciejmurawski.inpost.policies.discount.entity.PolicyType;
import com.maciejmurawski.inpost.policies.discount.web.PolicyFormDto;
import com.maciejmurawski.inpost.products.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.maciejmurawski.inpost.policies.discount.web.PolicyDtoFactory.createFormDto;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountPoliciesValidatorTest extends AbstractTest {

    @Autowired
    private DiscountPoliciesValidator objectUnderTest;

    @Test
    void shouldNotRaiseExceptionWhenValidatingNewValidPolicy() {
        //given
        Product product = productsFactory.createNewProduct();
        discountPolicyFactory.createAmountDiscountForProduct(product, 90, valueOf(10));
        PolicyFormDto formDto = createFormDto();

        //when
        //then
        assertThatNoException().isThrownBy(() -> objectUnderTest.validateNewPolicy(product.getId(), formDto));
    }

    @Test
    void shouldNotRaiseExceptionWhenValidatingExistingValidPolicy() {
        //given
        Product product = productsFactory.createNewProduct();
        DiscountPolicy existingPolicy = discountPolicyFactory.createAmountDiscountForProduct(product, 90, valueOf(10));
        PolicyFormDto formDto = createFormDto();

        //when
        //then
        assertThatNoException().isThrownBy(() -> objectUnderTest.validateExistingPolicy(product.getId(), existingPolicy.getId(), formDto));
    }

    @Test
    void shouldRaiseExceptionWhenValidatingNewDiscountWithNegativeProductsCount() {
        //given
        Product product = productsFactory.createNewProduct();
        PolicyFormDto formDto = createFormDto().toBuilder()
                .productsCount(-100)
                .build();

        //when
        //then
        assertThatThrownBy(() -> objectUnderTest.validateNewPolicy(product.getId(), formDto))
                .isInstanceOf(DiscountPolicyValidationException.class)
                .hasMessageContaining("Products count cannot be less than or equal to 0");
    }

    @Test
    void shouldRaiseExceptionWhenValidatingNewDiscountWithNegativeDiscountQuantity() {
        //given
        Product product = productsFactory.createNewProduct();
        PolicyFormDto formDto = createFormDto().toBuilder()
                .discountQuantity(valueOf(-100))
                .build();

        //when
        //then
        assertThatThrownBy(() -> objectUnderTest.validateNewPolicy(product.getId(), formDto))
                .isInstanceOf(DiscountPolicyValidationException.class)
                .hasMessageContaining("Discount quantity cannot be less than or equal to 0");
    }

    @Test
    void shouldRaiseExceptionWhenValidatingNewPercentageDiscountWithTooHighDiscountQuantity() {
        //given
        Product product = productsFactory.createNewProduct();
        PolicyFormDto formDto = createFormDto().toBuilder()
                .type(PolicyType.PERCENTAGE)
                .discountQuantity(valueOf(1000))
                .build();

        //when
        //then
        assertThatThrownBy(() -> objectUnderTest.validateNewPolicy(product.getId(), formDto))
                .isInstanceOf(DiscountPolicyValidationException.class)
                .hasMessageContaining("Discount quantity for percentage type cannot be more than 100");
    }

    @Test
    void shouldRaiseExceptionWhenValidatingNewDiscountWithCollidingProductsIdAndCount() {
        //given
        Product product = productsFactory.createNewProduct();
        DiscountPolicy existingDiscountPolicy = discountPolicyFactory.createAmountDiscountForProduct(product, 100, valueOf(10));
        PolicyFormDto formDto = createFormDto().toBuilder()
                .type(PolicyType.PERCENTAGE)
                .productsCount(existingDiscountPolicy.getProductsCount())
                .discountQuantity(existingDiscountPolicy.getDiscountQuantity())
                .build();

        //when
        //then
        assertThatThrownBy(() -> objectUnderTest.validateNewPolicy(product.getId(), formDto))
                .isInstanceOf(DiscountPolicyValidationException.class)
                .hasMessageContaining("Discount for product %s and product count 100 already exists".formatted(product.getId()));
    }

    @Test
    void shouldRaiseExceptionWhenValidatingUpdatedDiscountWithCollidingProductsIdAndCount() {
        //given
        Product product = productsFactory.createNewProduct();
        DiscountPolicy existingDiscountPolicy = discountPolicyFactory.createAmountDiscountForProduct(product, 100, valueOf(10));
        DiscountPolicy toUpdateDiscountPolicy = discountPolicyFactory.createAmountDiscountForProduct(product, 1000, valueOf(100));
        PolicyFormDto formDto = createFormDto().toBuilder()
                .type(PolicyType.PERCENTAGE)
                .productsCount(existingDiscountPolicy.getProductsCount())
                .discountQuantity(existingDiscountPolicy.getDiscountQuantity())
                .build();

        //when
        //then
        assertThatThrownBy(() -> objectUnderTest.validateExistingPolicy(product.getId(), toUpdateDiscountPolicy.getId(), formDto))
                .isInstanceOf(DiscountPolicyValidationException.class)
                .hasMessageContaining("Discount for product %s and product count 100 already exists".formatted(product.getId()));
    }

}