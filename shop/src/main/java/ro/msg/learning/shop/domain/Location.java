package ro.msg.learning.shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "location")
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseEntity {
    private String name;
    private String country;
    private String city;
    private String county;
    private String streetAddress;
}