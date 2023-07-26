package ro.msg.learning.shop.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.ProductCategoryMapper;
import ro.msg.learning.shop.service.ProductCategoryService;

import java.util.List;
import java.util.UUID;

@RequestMapping("/productCategory")
@RestController
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @PostMapping
    public ResponseEntity<ProductCategoryDto> createProductCategory(@RequestBody @NonNull ProductCategoryDto body) {
        ProductCategoryDto productCategoryDto = productCategoryService.createProductCategory(body.getName(), body.getDescription());
        return new ResponseEntity<>(productCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> getProductCategoryById(@PathVariable UUID id) {
        ProductCategory productCategory = productCategoryService.findById(id);
        return new ResponseEntity<>(productCategoryMapper.toDto(productCategory), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryDto>> getAllProductCategories() {
        List<ProductCategoryDto> productCategoryDtos = productCategoryService.getAllProductCategories();
        return ResponseEntity.ok(productCategoryDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable UUID id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> updateProductCategory(@PathVariable UUID id, @RequestBody ProductCategoryDto productCategoryDto) {
        try {
            ProductCategoryDto updatedProductCategoryDto = productCategoryService.updateProductCategory(id, productCategoryDto.getName(), productCategoryDto.getDescription());
            return ResponseEntity.ok(updatedProductCategoryDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

