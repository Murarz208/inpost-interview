package com.maciejmurawski.inpost.policies.discount.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountPolicy {

    @Id
    @UuidGenerator
    @Setter(AccessLevel.NONE)
    @ToString.Include
    private UUID id;

    @NotNull
    private UUID productId;

    @Min(1)
    private long productsCount;

    @NotNull
    private BigDecimal discountQuantity;

    @NotNull
    @ToString.Include
    @Enumerated(value = EnumType.STRING)
    private PolicyType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountPolicy other))
            return false;
        return id != null &&
               id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
