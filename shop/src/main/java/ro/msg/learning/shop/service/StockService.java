package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.domain.StockId;
import ro.msg.learning.shop.dto.ProductIdAndQuantityDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.exception.StockUpdateException;
import ro.msg.learning.shop.mapper.StockMapper;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StockService {
    public static final String STOCK_WITH_ID = "Stock with ID '";
    public static final String NOT_FOUND = "' not found.";
    public static final String PRODUCT_WITH_ID = "Product with ID '";
    public static final String LOCATION_WITH_ID = "Location with ID '";
    public static final String PRODUCT_NOT_FOUND_WITH_ID = "Product not found with ID:";
    public static final String STOCK_NOT_FOUND_FOR_PRODUCT_WITH_ID = "Stock not found for product with ID: ";
    public static final String INSUFFICIENT_STOCK = "Insufficient stock";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StockMapper stockMapper;

    public List<StockDto> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stockMapper.mapStocksToStockGetDtos(stocks);
    }

    public StockDto getStockById(StockId stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_WITH_ID + stockId + NOT_FOUND));
        return stockMapper.toDto(stock);
    }

    public StockDto createStock(StockDto stockDto) {
        Product product = productService.findById(stockDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_WITH_ID + stockDto.getProductId() + NOT_FOUND));

        Location location = locationService.findById(stockDto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException(LOCATION_WITH_ID + stockDto.getLocationId() + NOT_FOUND));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(stockDto.getQuantity());

        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    public StockDto updateStock(StockId stockId, StockDto stockDto) {
        validateProductAndLocationExistence(stockDto.getProductId(), stockDto.getLocationId());

        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_WITH_ID + stockId + NOT_FOUND));

        Product product = productService.findById(stockDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_WITH_ID + stockDto.getProductId() + NOT_FOUND));

        Location location = locationService.findById(stockDto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException(LOCATION_WITH_ID + stockDto.getLocationId() + NOT_FOUND));

        stockMapper.toStock(stockDto, product, location);
        existingStock.setProduct(product);
        existingStock.setLocation(location);
        existingStock.setQuantity(stockDto.getQuantity());

        stockRepository.save(existingStock);
        return stockMapper.toDto(existingStock);
    }

    public void deleteStock(StockId stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException(STOCK_WITH_ID + stockId + NOT_FOUND);
        }
        stockRepository.deleteById(stockId);
    }

    private void validateProductAndLocationExistence(UUID productId, UUID locationId) {
        if (!productService.existsById(productId)) {
            throw new ResourceNotFoundException(PRODUCT_WITH_ID + productId + NOT_FOUND);
        }

        if (!locationService.existsById(locationId)) {
            throw new ResourceNotFoundException(LOCATION_WITH_ID + locationId + NOT_FOUND);
        }
    }

    public boolean hasSufficientStock(Location location, List<ProductIdAndQuantityDto> productList) {
        for (ProductIdAndQuantityDto dto : productList) {
            Optional<Product> product = productService.findById(dto.getProductId());
            if (product.isEmpty()) {
                throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID + dto.getProductId());
            }

            Stock stock = stockRepository.findByLocationAndProduct(location, product.get());

            if (stock == null || stock.getQuantity() < dto.getQuantity()) {
                return false;
            }
        }

        return true;
    }

    public void updateStockQuantities(Location location, List<ProductIdAndQuantityDto> productList) {
        for (ProductIdAndQuantityDto productDto : productList) {
            Optional<Product> product=productService.findById(productDto.getProductId());
            if(product.isEmpty())
            {
                throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
            }

            Stock stock = stockRepository.findByLocationAndProduct(location,product.get());

            if (stock == null) {
                throw new ResourceNotFoundException(STOCK_NOT_FOUND_FOR_PRODUCT_WITH_ID + productDto.getProductId());
            }

            int newQuantity = stock.getQuantity() - productDto.getQuantity();
            if (newQuantity < 0) {
                throw new StockUpdateException(INSUFFICIENT_STOCK);
            }
            stock.setQuantity(newQuantity);
        }
    }

    public List<Location> findLocationsByProducts(List<Product> products, int size) {
        return stockRepository.findLocationsByProducts(products, size);
    }

    public List<Object[]> findLocationsMostAbundant(List<Product> products) {
        return stockRepository.findLocationsMostAbundant(products);
    }
}
