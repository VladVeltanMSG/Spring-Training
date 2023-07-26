package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductAndCategoryDto;
import ro.msg.learning.shop.exception.DuplicateResourceException;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    public static final String PRODUCT_WITH_NAME = "Product with name ";
    public static final String ALREADY_EXISTS = " already exists.";
    public static final String PRODUCT_NOT_FOUND_WITH_ID = "Product not found with ID: ";
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductAndCategoryDto createProductAndCategory(ProductAndCategoryDto productAndCategoryDto) {
        String productName = productAndCategoryDto.getProductName();

        if (productRepository.existsByName(productName)) {
            throw new DuplicateResourceException(PRODUCT_WITH_NAME + productName + ALREADY_EXISTS);
        }

        Product product = productMapper.toEntity(productAndCategoryDto);

        productRepository.save(product);

        return productMapper.toDto(product);
    }

    private ProductCategory getOrCreateProductCategory(ProductAndCategoryDto productAndCategoryDto) {
        String productCategoryName = productAndCategoryDto.getProductCategoryName();
        ProductCategory productCategory = productCategoryRepository.findByName(productCategoryName);

        if (productCategory == null) {
            productCategory = new ProductCategory();
            productCategory.setName(productCategoryName);
            productCategory.setDescription(productAndCategoryDto.getProductCategoryDescription());
            productCategory = productCategoryRepository.save(productCategory);
        }

        return productCategory;
    }

    public List<ProductAndCategoryDto> getAllProductDtos() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    public ProductAndCategoryDto getProductDtoById(UUID id) {
        Optional<Product> product = findById(id);
        if (product.isPresent()) {
            return productMapper.toDto(product.get());
        } else {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID + id);
        }
    }

    public boolean deleteProductById(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductAndCategoryDto updateProductById(UUID id, ProductAndCategoryDto productAndCategoryDto) {
        Product product = findById(id).orElseThrow(()->new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID + id));

        ProductCategory productCategory = getOrCreateProductCategory(productAndCategoryDto);

        product.setName(productAndCategoryDto.getProductName());
        product.setDescription(productAndCategoryDto.getProductDescription());
        product.setPrice(productAndCategoryDto.getPrice());
        product.setWeight(productAndCategoryDto.getWeight());
        product.setSupplier(productAndCategoryDto.getSupplier());
        product.setImageUrl(productAndCategoryDto.getImageUrl());
        product.setCategory(productCategory);

        productRepository.save(product);

        return productMapper.toDto(product);
    }

    public Optional<Product> findById(UUID productId) {
        return productRepository.findById(productId);
    }

    public boolean existsById(UUID productId) {
        return productRepository.existsById(productId);
    }
}

