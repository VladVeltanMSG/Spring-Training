package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.domain.StockId;
import ro.msg.learning.shop.dto.ProductIdAndQuantityDto;
import ro.msg.learning.shop.dto.StockCreateDto;
import ro.msg.learning.shop.dto.StockGetDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.exception.StockUpdateException;
import ro.msg.learning.shop.mapper.StockMapper;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import java.util.List;
import java.util.UUID;

@Service
public class StockService {
    public static final String STOCK_WITH_ID = "Stock with ID '";
    public static final String NOT_FOUND = "' not found.";
    public static final String PRODUCT_WITH_ID = "Product with ID '";
    public static final String LOCATION_WITH_ID = "Location with ID '";
    public static final String PRODUCT_NOT_FOUND_WITH_ID = "Product not found with ID:";
    public static final String PRODUCT_WITH_ID1 = "Stock not found for product with ID: ";
    public static final String INSUFFICIENT_STOCK = "Insufficient stock";
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private StockMapper stockMapper;

    public List<StockGetDto> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stockMapper.mapStocksToStockGetDtos(stocks);
    }

    public StockGetDto getStockById(StockId stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_WITH_ID + stockId + NOT_FOUND));
        return stockMapper.mapStockToStockGetDto(stock);
    }

    public StockGetDto createStock(StockCreateDto stockCreateDto) {
        Product product = productRepository.findById(stockCreateDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_WITH_ID + stockCreateDto.getProductId() + NOT_FOUND));

        Location location = locationRepository.findById(stockCreateDto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException(LOCATION_WITH_ID + stockCreateDto.getLocationId() + NOT_FOUND));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(stockCreateDto.getQuantity());

        stock = stockRepository.save(stock);
        return stockMapper.mapStockToStockGetDto(stock);
    }

    public StockGetDto updateStock(StockId stockId, StockCreateDto stockCreateDto) {
        validateProductAndLocationExistence(stockCreateDto.getProductId(), stockCreateDto.getLocationId());

        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_WITH_ID + stockId + NOT_FOUND));

        Product product = productRepository.getById(stockCreateDto.getProductId());
        Location location = locationRepository.getById(stockCreateDto.getLocationId());

        stockMapper.mapStockCreateDtoToStock(stockCreateDto, product, location);
        existingStock.setProduct(product);
        existingStock.setLocation(location);
        existingStock.setQuantity(stockCreateDto.getQuantity());

        stockRepository.save(existingStock);
        return stockMapper.mapStockToStockGetDto(existingStock);
    }

    public void deleteStock(StockId stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException(STOCK_WITH_ID + stockId + NOT_FOUND);
        }
        stockRepository.deleteById(stockId);
    }

    private void validateProductAndLocationExistence(UUID productId, UUID locationId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException(PRODUCT_WITH_ID + productId + NOT_FOUND);
        }

        if (!locationRepository.existsById(locationId)) {
            throw new ResourceNotFoundException(LOCATION_WITH_ID + locationId + NOT_FOUND);
        }
    }

    public boolean hasSufficientStock(Location location, List<ProductIdAndQuantityDto> productList) {
        for (ProductIdAndQuantityDto dto : productList) {
            Product product = productRepository.getProductById(dto.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID  + dto.getProductId());
            }

            Stock stock = stockRepository.findByLocationAndProduct(location, product);

            if (stock == null || stock.getQuantity() < dto.getQuantity()) {
                return false;
            }
        }

        return true;
    }

    @Transactional
    public void updateStockQuantities(Location location, List<ProductIdAndQuantityDto> productList) {
        for (ProductIdAndQuantityDto productDto : productList) {

            Stock stock = stockRepository.findByLocationAndProduct(location, productRepository.getProductById(productDto.getProductId()));

            if (stock == null) {
                throw new ResourceNotFoundException(PRODUCT_WITH_ID1 + productDto.getProductId());
            }

            int newQuantity = stock.getQuantity() - productDto.getQuantity();
            if(newQuantity<0)
            {
                throw new StockUpdateException(INSUFFICIENT_STOCK);
            }
            stock.setQuantity(newQuantity);
        }
    }
}
