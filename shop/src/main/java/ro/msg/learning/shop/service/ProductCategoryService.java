package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.exception.DuplicateResourceException;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.ProductCategoryMapper;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCategoryService {

    public static final String PRODUCT_CATEGORY_WITH_ID = "Product category with ID '";
    public static final String NOT_FOUND = "' not found.";
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists";
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryDto createProductCategory(String name, String description) {
        if (categoryExists(name)) {
            throw new DuplicateResourceException(CATEGORY_ALREADY_EXISTS);
        }
        ProductCategory productCategory = ProductCategory.builder().name(name).description(description).build();
        productCategoryRepository.save(productCategory);
        return productCategoryMapper.toDto(productCategory);
    }

    public void deleteProductCategory(UUID id) {
        productCategoryRepository.deleteById(id);
    }

    public ProductCategoryDto updateProductCategory(UUID id, String name, String description) {
        ProductCategory existingProductCategory = findById(id);
        existingProductCategory.setName(name);
        existingProductCategory.setDescription(description);
        productCategoryRepository.save(existingProductCategory);
        return productCategoryMapper.toDto(existingProductCategory);
    }

    public List<ProductCategoryDto> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        return productCategoryMapper.toDtoList(productCategories);
    }

    public ProductCategory findById(UUID id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_CATEGORY_WITH_ID + id + NOT_FOUND));
    }

    private boolean categoryExists(String name) {
        return productCategoryRepository.existsByName(name);
    }
}
