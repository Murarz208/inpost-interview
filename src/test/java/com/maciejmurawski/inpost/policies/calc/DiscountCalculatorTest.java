package com.maciejmurawski.inpost.policies.calc;

import com.maciejmurawski.inpost.AbstractTest;
import com.maciejmurawski.inpost.money.MonetaryAmount;
import com.maciejmurawski.inpost.policies.calc.web.CalculationResultsDto;
import com.maciejmurawski.inpost.products.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.maciejmurawski.inpost.assertions.CalculationResultsDtoAssertions.assertThat;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountCalculatorTest extends AbstractTest {

    @Autowired
    private DiscountCalculator objectUnderTest;

    private static MonetaryAmount calculateExpectedBasePrice(Product product, long amountToDiscount) {
        return MonetaryAmount.of(product.getPrice().getAmount().multiply(valueOf(amountToDiscount)), product.getPrice());
    }

    @Test
    void shouldCalculateDiscountForAmountDiscount() {
        //given
        Product product = productsFactory.createNewProduct(100, valueOf(100));
        discountPolicyFactory.createAmountDiscountForProduct(product, 10, valueOf(5));
        MonetaryAmount expectedDiscountedPrice = MonetaryAmount.of(valueOf(995), product.getPrice());
        long amountToDiscount = 10L;
        MonetaryAmount expectedBasePrice = calculateExpectedBasePrice(product, amountToDiscount);

        //when
        CalculationResultsDto calculationResult = objectUnderTest.calculateDiscountedPrice(product.getId(), amountToDiscount);

        //then
        assertThat(calculationResult)
                .hasDiscountedPrice(expectedDiscountedPrice)
                .hasBasePrice(expectedBasePrice)
                .hasProductsCountBefore(product.getAmount())
                .hasProductsCountAfter(product.getAmount() - amountToDiscount);
    }

    @Test
    void shouldCalculateDiscountForPercentageDiscount() {
        //given
        Product product = productsFactory.createNewProduct(1000, valueOf(100));
        discountPolicyFactory.createPercentageDiscountForProduct(product, 50, valueOf(0.1));
        MonetaryAmount expectedDiscountedPrice = MonetaryAmount.of(valueOf(4500), product.getPrice());
        long amountToDiscount = 50L;
        MonetaryAmount expectedBasePrice = calculateExpectedBasePrice(product, amountToDiscount);

        //when
        CalculationResultsDto calculationResult = objectUnderTest.calculateDiscountedPrice(product.getId(), amountToDiscount);

        //then
        assertThat(calculationResult)
                .hasDiscountedPrice(expectedDiscountedPrice)
                .hasBasePrice(expectedBasePrice)
                .hasProductsCountBefore(product.getAmount())
                .hasProductsCountAfter(product.getAmount() - amountToDiscount);
    }

    @Test
    void shouldNotChangeResultIfNoDiscountWasFound() {
        //given
        Product product = productsFactory.createNewProduct(1000, valueOf(100));
        MonetaryAmount expectedDiscountedPrice = MonetaryAmount.of(valueOf(5000), product.getPrice());
        long amountToDiscount = 50L;
        MonetaryAmount expectedBasePrice = calculateExpectedBasePrice(product, amountToDiscount);

        //when
        CalculationResultsDto calculationResult = objectUnderTest.calculateDiscountedPrice(product.getId(), amountToDiscount);

        //then
        assertThat(calculationResult)
                .hasDiscountedPrice(expectedDiscountedPrice)
                .hasBasePrice(expectedBasePrice)
                .hasProductsCountBefore(product.getAmount())
                .hasProductsCountAfter(product.getAmount() - amountToDiscount);
    }

    @Test
    void shouldClipResultsTo0IfTooMuchDiscount() {
        //given
        Product product = productsFactory.createNewProduct(1000, valueOf(100));
        discountPolicyFactory.createAmountDiscountForProduct(product, 1, valueOf(1000));
        MonetaryAmount expectedDiscountedPrice = MonetaryAmount.of(ZERO, product.getPrice());
        long amountToDiscount = 5L;
        MonetaryAmount expectedBasePrice = calculateExpectedBasePrice(product, amountToDiscount);

        //when
        CalculationResultsDto calculationResult = objectUnderTest.calculateDiscountedPrice(product.getId(), amountToDiscount);

        //then
        assertThat(calculationResult)
                .hasDiscountedPrice(expectedDiscountedPrice)
                .hasBasePrice(expectedBasePrice)
                .hasProductsCountBefore(product.getAmount())
                .hasProductsCountAfter(product.getAmount() - amountToDiscount);
    }

    @Test
    void shouldOnlySelectOneDiscountPolicyAccordingToAmount() {
        //given
        Product product = productsFactory.createNewProduct(1000, valueOf(100));
        discountPolicyFactory.createAmountDiscountForProduct(product, 10, valueOf(10));
        discountPolicyFactory.createAmountDiscountForProduct(product, 20, valueOf(20));
        discountPolicyFactory.createAmountDiscountForProduct(product, 50, valueOf(50));
        discountPolicyFactory.createAmountDiscountForProduct(product, 100, valueOf(100));
        MonetaryAmount expectedDiscountedPrice = MonetaryAmount.of(valueOf(3880), product.getPrice());
        long amountToDiscount = 39L;
        MonetaryAmount expectedBasePrice = calculateExpectedBasePrice(product, amountToDiscount);

        //when
        CalculationResultsDto calculationResult = objectUnderTest.calculateDiscountedPrice(product.getId(), amountToDiscount);

        //then
        assertThat(calculationResult)
                .hasDiscountedPrice(expectedDiscountedPrice)
                .hasBasePrice(expectedBasePrice)
                .hasProductsCountBefore(product.getAmount())
                .hasProductsCountAfter(product.getAmount() - amountToDiscount);
    }


    @Test
    void shouldThrowAnExceptionIfTryingToCalculateMoreThanAvailableProducts() {
        //given
        Product product = productsFactory.createNewProduct(1000, valueOf(100));
        discountPolicyFactory.createAmountDiscountForProduct(product, 10, valueOf(10));

        //when
        //then
        assertThatThrownBy(() -> objectUnderTest.calculateDiscountedPrice(product.getId(), product.getAmount() * 2))
                .isInstanceOf(DiscountCalculationException.class)
                .hasMessageContaining("Amount is bigger than available product amount");
    }
}