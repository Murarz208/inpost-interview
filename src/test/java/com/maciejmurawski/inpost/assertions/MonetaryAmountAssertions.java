package com.maciejmurawski.inpost.assertions;

import com.maciejmurawski.inpost.money.MonetaryAmount;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.BigDecimalComparator;

import java.math.BigDecimal;

public class MonetaryAmountAssertions extends AbstractObjectAssert<MonetaryAmountAssertions, MonetaryAmount> {

    public MonetaryAmountAssertions(MonetaryAmount actual) {
        super(actual, MonetaryAmountAssertions.class);
    }

    public static MonetaryAmountAssertions assertThat(MonetaryAmount actual) {
        return new MonetaryAmountAssertions(actual);
    }

    // Assertion for the amount
    public MonetaryAmountAssertions hasAmount(BigDecimal expectedAmount) {
        isNotNull();
        Assertions.assertThat(actual.getAmount())
                .usingComparator(new BigDecimalComparator())
                .isEqualTo(expectedAmount);
        return this;
    }

    // Assertion for the currency
    public MonetaryAmountAssertions hasCurrencySameAs(MonetaryAmount expectedMonetaryAmount) {
        isNotNull();
        String expectedCurrency = expectedMonetaryAmount.getCurrency();
        if (actual.getCurrency() == null) {
            failWithMessage("Expected currency to be <%s> but was null", expectedCurrency);
        } else if (!actual.getCurrency().equals(expectedCurrency)) {
            failWithMessage("Expected currency to be <%s> but was <%s>", expectedCurrency, actual.getCurrency());
        }
        return this;
    }

}
