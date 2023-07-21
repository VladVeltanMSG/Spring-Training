package ro.msg.learning.shop.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Data
@Entity
@Table(name="stock")
@NoArgsConstructor
@IdClass(StockId.class)
public class Stock{

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @JoinColumn(name="product",insertable=false, updatable=false)
    private Product product;

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @JoinColumn(name="location",insertable=false, updatable=false)
    private Location location;

    private Integer quantity;
}
