package com.maciejmurawski.inpost.products;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ProductsMapper {

    ProductBaseDto toProductBaseDto(Product product);

}
