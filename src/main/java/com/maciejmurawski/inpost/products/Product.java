package com.maciejmurawski.inpost.products;

import com.maciejmurawski.inpost.money.MonetaryAmount;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @UuidGenerator
    @Setter(AccessLevel.NONE)
    @ToString.Include
    private UUID id;

    @Min(0)
    private long amount;

    @NotEmpty
    @ToString.Include
    private String name;

    @NotNull
    @Embedded
    @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    @AttributeOverride(name = "amount", column = @Column(name = "price_amount"))
    private MonetaryAmount price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product other))
            return false;
        return id != null &&
               id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
