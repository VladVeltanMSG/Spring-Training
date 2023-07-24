package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.domain.Customer;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetDto {
    private UUID id;
    private Customer customer;
    private LocalDateTime localDateTime;
    private String country;
    private String city;
    private String county;
    private String streetAdress;
}
