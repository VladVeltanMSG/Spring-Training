package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductAndCategoryDto;
import ro.msg.learning.shop.exception.DuplicateResourceException;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductAndCategoryDto createProductAndCategoryDto(ProductAndCategoryDto productAndCategoryDto) {
        String productName = productAndCategoryDto.getProductName();

        Product existingProduct = productRepository.findByName(productName);
        if (existingProduct != null) {
            throw new DuplicateResourceException("Product with name " + productName + " already exists.");
        }

        Product product = productMapper.toEntity(productAndCategoryDto);
        ProductCategory productCategory = getOrCreateProductCategory(productAndCategoryDto);
        product.setCategory(productCategory);

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
        Product product = getProductById(id);
        ProductAndCategoryDto productDto;
        if (product != null) {
            productDto = productMapper.toDto(product);
        } else {
            productDto = null;
        }
        return productDto;
    }

    public boolean deleteProductById(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductAndCategoryDto updateProductById(UUID id, ProductAndCategoryDto productAndCategoryDto) {
        Product product = getProductById(id);
        if (product == null) {
            return null;
        }

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

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }
}

