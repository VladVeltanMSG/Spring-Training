package ro.msg.learning.shop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.dto.OrderCreateDto;
import ro.msg.learning.shop.dto.ProductIdAndQuantityDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.service.CustomerService;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.StockService;

import java.util.ArrayList;
import java.util.List;

import static ro.msg.learning.shop.service.OrderService.CUSTOMER_NOT_FOUND;
import static ro.msg.learning.shop.service.OrderService.NO_LOCATION_WITH_SUFFICIENT_STOCK_FOUND;
import static ro.msg.learning.shop.service.StockService.PRODUCT_NOT_FOUND_WITH_ID;


public class SingleLocationSelectionStrategy implements LocationSelectionStrategy {

    @Autowired
    private StockService stockService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order selectLocations(OrderCreateDto orderCreateDto) {
        List<Location> locationsList = stockService.findLocationsByProducts(convertToProductList(orderCreateDto), convertToProductList(orderCreateDto).size());

        Customer customer = customerService.findById(orderCreateDto.getIdCustomer()).orElseThrow(() -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));

        if (!updateFirstLocationFoundWithSufficientStock(locationsList, orderCreateDto.getProductList())) {
            throw new ResourceNotFoundException(NO_LOCATION_WITH_SUFFICIENT_STOCK_FOUND);
        }

        return orderMapper.orderCreateDtoToOrder(orderCreateDto, customer);

    }

    public boolean updateFirstLocationFoundWithSufficientStock(List<Location> locationsList, List<ProductIdAndQuantityDto> productIdAndQuantityDtoList) {
        boolean ok = false;
        for (Location location : locationsList) {
            if (stockService.hasSufficientStock(location, productIdAndQuantityDtoList)) {
                stockService.updateStockQuantities(location, productIdAndQuantityDtoList);
                ok = true;
                break;
            }
        }
        return ok;
    }

    public List<Product> convertToProductList(OrderCreateDto orderCreateDto) {
        List<ProductIdAndQuantityDto> productIdAndQuantityList = orderCreateDto.getProductList();
        List<Product> productList = new ArrayList<>();

        for (ProductIdAndQuantityDto productIdAndQuantityDto : productIdAndQuantityList) {
            Product product = productService.getProductById(productIdAndQuantityDto.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID + productIdAndQuantityDto.getProductId());
            }
            productList.add(product);
        }
        return productList;
    }
}

