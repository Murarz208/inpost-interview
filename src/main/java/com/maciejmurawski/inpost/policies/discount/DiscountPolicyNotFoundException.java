package com.maciejmurawski.inpost.policies.discount;

import java.util.UUID;

class DiscountPolicyNotFoundException extends RuntimeException {
    public DiscountPolicyNotFoundException(UUID policyId) {
        super("Can't find policy with id %s".formatted(policyId));
    }
}
