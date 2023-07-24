package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.dto.OrderCreateDto;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.strategy.LocationSelectionStrategy;

@Service
public class OrderService {
    public static final String CUSTOMER_NOT_FOUND = "Customer not found!";
    public static final String NO_LOCATION_WITH_SUFFICIENT_STOCK_FOUND = "No location with sufficient stock found";
    public static final String PRODUCT_NOT_FOUND_WITH_ID = "Product not found with ID: ";
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private LocationSelectionStrategy locationSelectionStrategy;

    public Order createOrder(OrderCreateDto orderCreateDto) {
        return locationSelectionStrategy.selectLocations(orderCreateDto);

    }


}
