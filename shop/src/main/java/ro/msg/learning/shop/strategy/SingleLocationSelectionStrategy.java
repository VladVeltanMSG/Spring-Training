package ro.msg.learning.shop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.StockService;

import java.util.List;

@Component
public class SingleLocationSelectionStrategy implements LocationSelectionStrategy {

    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Location> selectLocations(List<Product> products) {
        return stockRepository.findLocationsByProducts(products, products.size());

    }
}

