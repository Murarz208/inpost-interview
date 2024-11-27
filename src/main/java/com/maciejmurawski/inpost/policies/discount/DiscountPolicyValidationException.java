package com.maciejmurawski.inpost.policies.discount;

class DiscountPolicyValidationException extends RuntimeException {
    public DiscountPolicyValidationException(String message) {
        super(message);
    }
}
