package ro.msg.learning.shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "product_category")
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory extends BaseEntity {
    private String name;
    private String description;
}
