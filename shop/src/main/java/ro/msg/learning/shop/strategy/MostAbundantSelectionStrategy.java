package ro.msg.learning.shop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.dto.OrderCreateDto;
import ro.msg.learning.shop.dto.ProductIdAndQuantityDto;
import ro.msg.learning.shop.service.StockService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MostAbundantSelectionStrategy implements LocationSelectionStrategy {

    @Autowired
    private StockService stockService;


    @Override
    public List<Location> selectLocations(List<Product> products) {
        return null;
    }
}

