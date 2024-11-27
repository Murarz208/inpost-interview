package com.maciejmurawski.inpost.policies.discount;

import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiscountPoliciesQueryService {

    private final DiscountPoliciesRepository discountPoliciesRepository;

    @Transactional(readOnly = true)
    public Page<DiscountPolicy> retrieveAll(UUID productId, Pageable page) {
        return discountPoliciesRepository.findAllByProductId(productId, page);
    }

    @Transactional(readOnly = true)
    public DiscountPolicy retrieveOne(UUID policyId) {
        return discountPoliciesRepository.findDiscountPolicyById(policyId);
    }

    @Transactional(readOnly = true)
    public Optional<DiscountPolicy> retrieveOneForCalculation(UUID productId, long amount) {
        return discountPoliciesRepository.findTopByProductIdAndProductsCountLessThanEqualOrderByProductIdDesc(productId, amount);
    }

}
