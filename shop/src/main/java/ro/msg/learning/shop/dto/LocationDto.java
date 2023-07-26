package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private UUID id;
    private String name;
    private String country;
    private String city;
    private String county;
    private String streetAddress;

}
