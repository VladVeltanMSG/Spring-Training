package ro.msg.learning.shop.domain;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StockId implements Serializable {
    private UUID product;
    private UUID location;
}
