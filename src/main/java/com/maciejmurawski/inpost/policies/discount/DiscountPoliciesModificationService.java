package com.maciejmurawski.inpost.policies.discount;

import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import com.maciejmurawski.inpost.policies.discount.web.PolicyFormDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class DiscountPoliciesModificationService {

    private final DiscountPoliciesRepository discountPoliciesRepository;
    private final DiscountPoliciesValidator discountPoliciesValidator;
    private final DiscountPolicyMapper discountPolicyMapper;

    @Transactional
    public DiscountPolicy create(UUID productId, PolicyFormDto policyDto) {
        discountPoliciesValidator.validateNewPolicy(productId, policyDto);
        log.debug("Trying to create new policy for product [productId={}]", productId);
        DiscountPolicy createdPolicy = new DiscountPolicy();
        createdPolicy.setProductId(productId);
        discountPolicyMapper.updatePolicyFromFormDto(policyDto, createdPolicy);
        createdPolicy = discountPoliciesRepository.save(createdPolicy);
        log.info("Successfully created new policy [policyId={}] for product [productId={}]", createdPolicy.getId(), productId);
        return createdPolicy;
    }

    @Transactional
    public DiscountPolicy update(UUID productId, UUID policyId, PolicyFormDto policyDto) {
        discountPoliciesValidator.validateExistingPolicy(productId, policyId, policyDto);
        log.debug("Trying to update policy [policyId={}]", policyId);
        DiscountPolicy updatedPolicy = discountPoliciesRepository.findDiscountPolicyById(policyId);
        discountPolicyMapper.updatePolicyFromFormDto(policyDto, updatedPolicy);
        log.info("Successfully updated policy [policyId={}]", updatedPolicy.getId());
        return updatedPolicy;
    }

    public void delete(UUID policyId) {
        log.info("Trying to delete policy [policyId={}]", policyId);
        discountPoliciesRepository.deleteById(policyId);
        log.info("Successfully deleted policy [policyId={}]", policyId);
    }
}
