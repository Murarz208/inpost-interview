package com.maciejmurawski.inpost.policies.discount.web;

import com.maciejmurawski.inpost.policies.discount.DiscountPoliciesModificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{productId}/policies")
class PoliciesModificationController {

    private final DiscountPoliciesModificationService discountPoliciesModificationService;

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    void create(@PathVariable UUID productId, @RequestBody PolicyFormDto policyDto) {
        discountPoliciesModificationService.create(productId, policyDto);
    }

    @PutMapping("{policyId}")
    @ResponseStatus(NO_CONTENT)
    void update(@PathVariable UUID productId, @PathVariable UUID policyId, @RequestBody PolicyFormDto policyDto) {
        discountPoliciesModificationService.update(productId, policyId, policyDto);
    }

    @DeleteMapping("{policyId}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable UUID productId, @PathVariable UUID policyId) {
        discountPoliciesModificationService.delete(policyId);
    }


}
