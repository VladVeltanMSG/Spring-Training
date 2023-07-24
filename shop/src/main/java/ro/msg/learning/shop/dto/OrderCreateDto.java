package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderCreateDto {
    private UUID idCustomer;
    private LocalDateTime localDateTime;
    private String country;
    private String city;
    private String county;
    private String streetAddress;
    private List<ProductIdAndQuantityDto> productList;
}
