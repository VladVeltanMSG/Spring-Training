package ro.msg.learning.shop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "orderr") // "order" is a reserved keyword, so we use "orderr" as the table name
@Builder
@AllArgsConstructor
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "address_country")
    private String addressCountry;

    @Column(name = "address_city")
    private String addressCity;

    @Column(name = "address_county")
    private String addressCounty;

    @Column(name = "address_street")
    private String addressStreet;
}
