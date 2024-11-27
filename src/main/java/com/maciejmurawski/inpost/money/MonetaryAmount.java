package com.maciejmurawski.inpost.money;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Currency;

import java.math.BigDecimal;

import static lombok.AccessLevel.PACKAGE;

@Data
@AllArgsConstructor(access = PACKAGE)
@NoArgsConstructor
@Embeddable
public class MonetaryAmount {

    @Min(0)
    private BigDecimal amount;

    @Currency({"PLN", "EUR", "USD"})
    private String currency;

    public static MonetaryAmount pln(BigDecimal amount) {
        return new MonetaryAmount(amount, "PLN");
    }

    public static MonetaryAmount eur(BigDecimal amount) {
        return new MonetaryAmount(amount, "EUR");
    }

    public static MonetaryAmount usd(BigDecimal amount) {
        return new MonetaryAmount(amount, "USD");
    }

    public static MonetaryAmount of(BigDecimal amount, MonetaryAmount existingMonetaryAmount) {
        return new MonetaryAmount(amount, existingMonetaryAmount.getCurrency());
    }

}
