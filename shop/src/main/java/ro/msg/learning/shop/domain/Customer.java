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
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String emailAddress;
}