package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.dto.StockCreateDto;
import ro.msg.learning.shop.dto.StockGetDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {

    public Stock mapStockCreateDtoToStock(StockCreateDto stockCreateDto, Product product, Location location) {
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(stockCreateDto.getQuantity());
        return stock;
    }

    public StockGetDto mapStockToStockGetDto(Stock stock) {
        StockGetDto stockGetDto = new StockGetDto();
        stockGetDto.setProduct(stock.getProduct());
        stockGetDto.setLocation(stock.getLocation());
        stockGetDto.setQuantity(stock.getQuantity());
        return stockGetDto;
    }

    public List<StockGetDto> mapStocksToStockGetDtos(List<Stock> stocks) {
        return stocks.stream()
                .map(this::mapStockToStockGetDto)
                .collect(Collectors.toList());
    }
}
