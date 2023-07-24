package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.ProductCategoryMapper;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCategoryService {

    public static final String PRODUCT_CATEGORY_WITH_ID = "Product category with ID ";
    public static final String NOT_FOUND = " not found.";
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryDto createProductCategoryDto(String name, String description) {
        ProductCategory productCategory = productCategoryRepository.findByName(name);

        if (productCategory != null) {
            return null;
        }

        productCategory = ProductCategory.builder().name(name).description(description).build();
        productCategoryRepository.save(productCategory);
        return productCategoryMapper.toDto(productCategory);
    }

    public void deleteProductCategory(UUID id) {
        productCategoryRepository.deleteById(id);
    }

    public ProductCategory updateProductCategory(UUID id, String name, String description) {
        ProductCategory existingProductCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_CATEGORY_WITH_ID + "'" + id + "'" + NOT_FOUND));

        existingProductCategory.setName(name);
        existingProductCategory.setDescription(description);

        return productCategoryRepository.save(existingProductCategory);
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory findProductCategoryById(UUID id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_CATEGORY_WITH_ID + "'" + id + "'" + NOT_FOUND));
    }

    public ProductCategory findByName(String name) {
        return productCategoryRepository.findByName(name);
    }
}
