package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductCategoryMapper {
    public ProductCategoryDto toDto(ProductCategory productCategory)
    {
        return new ProductCategoryDto(productCategory.getId(),productCategory.getName(),productCategory.getDescription());
    }
    public ProductCategory toEntity(ProductCategoryDto productCategoryDto)
    {
        return new ProductCategory(productCategoryDto.getName(), productCategoryDto.getDescription());
    }
    public List<ProductCategoryDto> toDtoList(List<ProductCategory> productCategories) {
        return productCategories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
