package com.maciejmurawski.inpost.policies.discount;

import com.maciejmurawski.inpost.policies.discount.entity.DiscountPolicy;
import com.maciejmurawski.inpost.policies.discount.web.PolicyBaseDto;
import com.maciejmurawski.inpost.policies.discount.web.PolicyFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DiscountPolicyMapper {

    PolicyBaseDto toPolicyBaseDto(DiscountPolicy policy);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", ignore = true)
    void updatePolicyFromFormDto(PolicyFormDto formDto, @MappingTarget DiscountPolicy property);

}
