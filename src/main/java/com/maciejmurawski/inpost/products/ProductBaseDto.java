package com.maciejmurawski.inpost.products;

import com.maciejmurawski.inpost.money.MonetaryAmount;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

record ProductBaseDto(@NotNull UUID id, long amount, String name, MonetaryAmount price) {
}
