package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductAndCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public ProductAndCategoryDto toDto(Product product) {
        return ProductAndCategoryDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .supplier(product.getSupplier())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory().getId())
                .productCategoryName(product.getCategory().getName())
                .productCategoryDescription(product.getCategory().getDescription())
                .build();
    }

    public Product toEntity(ProductAndCategoryDto productAndCategoryDto) {
        ProductCategory productCategory = ProductCategory.builder()
                .name(productAndCategoryDto.getProductCategoryName())
                .description(productAndCategoryDto.getProductCategoryDescription())
                .build();

        return Product.builder()
                .name(productAndCategoryDto.getProductName())
                .description(productAndCategoryDto.getProductDescription())
                .price(productAndCategoryDto.getPrice())
                .weight(productAndCategoryDto.getWeight())
                .category(productCategory)
                .supplier(productAndCategoryDto.getSupplier())
                .imageUrl(productAndCategoryDto.getImageUrl())
                .build();
    }

    public List<ProductAndCategoryDto> toDtoList(List<Product> products) {
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

}
