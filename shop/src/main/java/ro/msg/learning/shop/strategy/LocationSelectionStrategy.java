package ro.msg.learning.shop.strategy;

import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;

import java.util.List;

public interface LocationSelectionStrategy {
    List<Location> selectLocations(List<Product> products);
}

