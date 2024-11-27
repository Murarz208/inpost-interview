package com.maciejmurawski.inpost.policies.discount;

import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface DiscountPoliciesRepository extends JpaRepository<DiscountPolicy, UUID> {

    Optional<DiscountPolicy> findTopByProductIdAndProductsCountLessThanEqualOrderByProductIdDesc(UUID productId, long productsCount);

    boolean existsByProductIdAndProductsCountEquals(UUID productId, long productsCount);

    boolean existsByIdNotAndProductIdAndProductsCountEquals(UUID policyId, UUID productId, long productsCount);

    default DiscountPolicy findDiscountPolicyById(UUID policyId) {
        return findById(policyId).orElseThrow(() -> new DiscountPolicyNotFoundException(policyId));
    }

    Page<DiscountPolicy> findAllByProductId(UUID productId, Pageable pageable);

}
