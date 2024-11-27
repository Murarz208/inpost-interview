package com.maciejmurawski.inpost.assertions;

import com.maciejmurawski.inpost.money.MonetaryAmount;
import com.maciejmurawski.inpost.policies.calc.web.CalculationResultsDto;
import org.assertj.core.api.AbstractObjectAssert;

import java.util.Objects;

public class CalculationResultsDtoAssertions extends AbstractObjectAssert<CalculationResultsDtoAssertions, CalculationResultsDto> {

    public CalculationResultsDtoAssertions(CalculationResultsDto actual) {
        super(actual, CalculationResultsDtoAssertions.class);
    }

    public static CalculationResultsDtoAssertions assertThat(CalculationResultsDto actual) {
        return new CalculationResultsDtoAssertions(actual);
    }

    // Assert for discountedPrice
    public CalculationResultsDtoAssertions hasDiscountedPrice(MonetaryAmount expectedDiscountedPrice) {
        isNotNull();
        MonetaryAmountAssertions.assertThat(actual.discountedPrice())
                .hasCurrencySameAs(expectedDiscountedPrice)
                .hasAmount(expectedDiscountedPrice.getAmount());
        return this;
    }

    // Assert for basePrice
    public CalculationResultsDtoAssertions hasBasePrice(MonetaryAmount expectedBasePrice) {
        isNotNull();
        MonetaryAmountAssertions.assertThat(actual.basePrice())
                .hasCurrencySameAs(expectedBasePrice)
                .hasAmount(expectedBasePrice.getAmount());
        return this;
    }

    // Assert for productsCountBefore
    public CalculationResultsDtoAssertions hasProductsCountBefore(long expectedProductsCountBefore) {
        isNotNull();
        if (!Objects.equals(expectedProductsCountBefore, actual.productsCountBefore())) {
            failWithMessage("Expected productsCountBefore to be <%s> but was <%s>", expectedProductsCountBefore, actual.productsCountBefore());
        }
        return this;
    }

    // Assert for productsCountAfter
    public CalculationResultsDtoAssertions hasProductsCountAfter(long expectedProductsCountAfter) {
        isNotNull();
        if (!Objects.equals(expectedProductsCountAfter, actual.productsCountAfter())) {
            failWithMessage("Expected productsCountAfter to be <%s> but was <%s>", expectedProductsCountAfter, actual.productsCountAfter());
        }
        return this;
    }

}
