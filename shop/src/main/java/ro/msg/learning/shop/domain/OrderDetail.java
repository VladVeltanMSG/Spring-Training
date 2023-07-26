package ro.msg.learning.shop.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@NoArgsConstructor
@Table(name = "order_detail")
@IdClass(OrderDetailId.class)
public class OrderDetail {

    @Id
    @JoinColumn(name = "orderr")
    @JdbcTypeCode(SqlTypes.UUID)
    private Order orderr;

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @JoinColumn(name = "product")
    private Product product;

    @JoinColumn(name = "shipped_from")
    @ManyToOne
    @JdbcTypeCode(SqlTypes.JSON)
    private Location shippedFrom;

    @Column(name = "quantity")
    private Integer quantity;
}
