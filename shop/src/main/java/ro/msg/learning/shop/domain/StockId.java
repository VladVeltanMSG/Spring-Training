package ro.msg.learning.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StockId implements Serializable {
    private UUID product;
    private UUID location;
}
