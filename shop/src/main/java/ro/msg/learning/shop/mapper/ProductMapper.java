package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductAndCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public ProductAndCategoryDto toDto(Product product)
    {
        return new ProductAndCategoryDto(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.getWeight(),product.getSupplier(),product.getImageUrl(),product.getCategory().getId(),product.getCategory().getName(),product.getCategory().getDescription());
    }
    public Product toEntity(ProductAndCategoryDto productAndCategoryDto)
    {
        ProductCategory productCategory=new ProductCategory(productAndCategoryDto.getProductCategoryName(), productAndCategoryDto.getProductDescription());
        return new Product(productAndCategoryDto.getProductName(),productAndCategoryDto.getProductCategoryDescription(),productAndCategoryDto.getPrice(),productAndCategoryDto.getWeight(),productCategory,productAndCategoryDto.getSupplier(),productAndCategoryDto.getImageUrl());
    }
    public List<ProductAndCategoryDto> toDtoList(List<Product> products) {
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

}
