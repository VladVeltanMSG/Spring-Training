package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAndCategoryDto {
    private UUID productId;
    private String productName;
    private String productDescription;
    private double price;
    private double weight;
    private String supplier;
    private String imageUrl;

    private UUID categoryId;
    private String productCategoryName;
    private String productCategoryDescription;


}
