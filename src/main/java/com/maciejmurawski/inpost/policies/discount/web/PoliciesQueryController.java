package com.maciejmurawski.inpost.policies.discount.web;

import com.maciejmurawski.inpost.policies.discount.DiscountPoliciesQueryService;
import com.maciejmurawski.inpost.policies.discount.DiscountPolicyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{productId}/policies")
class PoliciesQueryController {

    private final DiscountPoliciesQueryService discountPoliciesQueryService;
    private final DiscountPolicyMapper discountPolicyMapper;

    @GetMapping
    Page<PolicyBaseDto> retrieveAll(@PathVariable UUID productId, Pageable page) {
        return discountPoliciesQueryService.retrieveAll(productId, page).map(discountPolicyMapper::toPolicyBaseDto);
    }

    // `@PathVariable UUID productId` ignored on purpose
    @GetMapping("{policyId}")
    PolicyBaseDto retrieveOne(@PathVariable UUID policyId) {
        return discountPolicyMapper.toPolicyBaseDto(discountPoliciesQueryService.retrieveOne(policyId));
    }

}
