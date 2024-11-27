package com.maciejmurawski.inpost.policies.calc;

import com.maciejmurawski.inpost.money.MonetaryAmount;
import com.maciejmurawski.inpost.policies.calc.web.CalculationResultsDto;
import com.maciejmurawski.inpost.policies.discount.DiscountPoliciesQueryService;
import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import com.maciejmurawski.inpost.products.Product;
import com.maciejmurawski.inpost.products.ProductsQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static java.math.BigDecimal.ONE;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountCalculator {

    private final DiscountPoliciesQueryService discountPoliciesQueryService;
    private final ProductsQueryService productsQueryService;

    public CalculationResultsDto calculateDiscountedPrice(UUID productId, long amount) {
        log.debug("Started calculation for product {} with amount {}", productId, amount);
        Product product = productsQueryService.retrieveOne(productId);
        if (product.getAmount() < amount) {
            throw new DiscountCalculationException("Amount is bigger than available product amount");
        }

        Optional<DiscountPolicy> nullableDiscount = discountPoliciesQueryService.retrieveOneForCalculation(productId, amount);
        nullableDiscount.ifPresentOrElse(
                discountPolicy -> log.debug("Found discount {} for products {} discount calculation", discountPolicy.getId(), productId),
                () -> log.debug("No discount policy for product {}, using regular product price", productId)
        );

        BigDecimal priceForOrderedAmount = product.getPrice().getAmount().multiply(BigDecimal.valueOf(amount));
        BigDecimal discountedPrice = nullableDiscount
                .map(discountPolicy -> calculateDiscountedPrice(priceForOrderedAmount, discountPolicy))
                .orElse(priceForOrderedAmount);

        if (discountedPrice.signum() < 0) {
            log.debug("Calculation results for product {} are less than 0, returning 0", productId);
            discountedPrice = BigDecimal.ZERO;
        }
        log.info("Calculation results for product {}: {}", productId, discountedPrice);
        return CalculationResultsDto.builder()
                .productsCountAfter(product.getAmount() - amount)
                .productsCountBefore(product.getAmount())
                .basePrice(MonetaryAmount.of(priceForOrderedAmount, product.getPrice()))
                .discountedPrice(MonetaryAmount.of(discountedPrice, product.getPrice()))
                .build();
    }

    private BigDecimal calculateDiscountedPrice(BigDecimal productsPrice, DiscountPolicy discountPolicy) {
        return switch (discountPolicy.getType()) {
            case AMOUNT -> productsPrice.subtract(discountPolicy.getDiscountQuantity());
            case PERCENTAGE -> productsPrice.multiply(ONE.subtract(discountPolicy.getDiscountQuantity()));
        };
    }

}
