package com.maciejmurawski.inpost.policies.discount;

import com.maciejmurawski.inpost.AbstractTest;
import com.maciejmurawski.inpost.assertions.DiscountPolicyAssertions;
import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import com.maciejmurawski.inpost.policies.discount.web.PolicyFormDto;
import com.maciejmurawski.inpost.products.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.maciejmurawski.inpost.policies.discount.web.PolicyDtoFactory.createFormDto;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountPoliciesModificationServiceTest extends AbstractTest {

    @Autowired
    private DiscountPoliciesModificationService discountPoliciesModificationService;

    @Autowired
    private DiscountPoliciesQueryService discountPoliciesQueryService;

    @Test
    void shouldCreateNewDiscountPolicy() {
        //given
        Product product = productsFactory.createNewProduct();
        PolicyFormDto formDto = createFormDto();

        //when
        DiscountPolicy createdPolicy = discountPoliciesModificationService.create(product.getId(), formDto);

        //then
        DiscountPolicyAssertions.assertThat(createdPolicy)
                .hasNonNullId()
                .hasProductId(product.getId())
                .hasDiscountQuantity(formDto.discountQuantity())
                .hasType(formDto.type())
                .hasProductsCount(product.getAmount());
    }

    @Test
    void shouldUpdateExistingNewDiscountPolicy() {
        //given
        Product product = productsFactory.createNewProduct();
        DiscountPolicy policyToUpdate = discountPolicyFactory.createAmountDiscountForProduct(product, 90, valueOf(10));
        PolicyFormDto formDto = createFormDto();

        //when
        DiscountPolicy updatedPolicy = discountPoliciesModificationService.update(product.getId(), policyToUpdate.getId(), formDto);

        //then
        DiscountPolicyAssertions.assertThat(updatedPolicy)
                .hasNonNullId()
                .hasProductId(product.getId())
                .hasDiscountQuantity(formDto.discountQuantity())
                .hasType(formDto.type())
                .hasProductsCount(product.getAmount());
    }

    @Test
    void shouldDeleteExistingDiscountPolicy() {
        //given
        Product product = productsFactory.createNewProduct();
        DiscountPolicy policyToDelete = discountPolicyFactory.createAmountDiscountForProduct(product, 90, valueOf(10));

        //when
        discountPoliciesModificationService.delete(policyToDelete.getId());

        //then
        assertThatThrownBy(() -> discountPoliciesQueryService.retrieveOne(policyToDelete.getId()))
                .isInstanceOf(DiscountPolicyNotFoundException.class);
    }
}