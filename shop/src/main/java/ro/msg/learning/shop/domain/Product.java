package ro.msg.learning.shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    private String name;
    private String description;
    private double price;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "category")
    private ProductCategory category;

    private String supplier;
    private String imageUrl;
}