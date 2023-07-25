package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductCategoryMapper {
    public ProductCategoryDto toDto(ProductCategory productCategory) {
        return ProductCategoryDto.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }

    public List<ProductCategoryDto> toDtoList(List<ProductCategory> productCategories) {
        return productCategories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
