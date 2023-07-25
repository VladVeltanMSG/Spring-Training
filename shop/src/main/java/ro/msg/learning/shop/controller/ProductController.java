package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductAndCategoryDto;
import ro.msg.learning.shop.exception.DuplicateResourceException;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.service.ProductService;

import java.util.List;
import java.util.UUID;

@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductAndCategoryDto> createProductAndCategory(@RequestBody ProductAndCategoryDto productAndCategoryDto) {
        try {
            ProductAndCategoryDto createdProduct = productService.createProductAndCategory(productAndCategoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (DuplicateResourceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductAndCategoryDto>> getAllProducts() {
        List<ProductAndCategoryDto> productDtos = productService.getAllProductDtos();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductAndCategoryDto> getProductById(@PathVariable UUID id) {
        ProductAndCategoryDto productDto = productService.getProductDtoById(id);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
        boolean deleted = productService.deleteProductById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductAndCategoryDto> updateProductById(@PathVariable UUID id, @RequestBody ProductAndCategoryDto productAndCategoryDto) {
        try {
            ProductAndCategoryDto updatedProductDto = productService.updateProductById(id, productAndCategoryDto);
            return ResponseEntity.ok(updatedProductDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
