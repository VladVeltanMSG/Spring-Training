package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.dto.StockDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {

    public Stock toStock(StockDto stockDto, Product product, Location location) {
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(stockDto.getQuantity());
        return stock;
    }

    public StockDto toDto(Stock stock) {
        StockDto stockDto = new StockDto();
        stockDto.setProductId(stock.getProduct().getId());
        stockDto.setLocationId(stock.getLocation().getId());
        stockDto.setQuantity(stock.getQuantity());
        return stockDto;
    }

    public List<StockDto> mapStocksToStockGetDtos(List<Stock> stocks) {
        return stocks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
