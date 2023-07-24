package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.dto.OrderCreateDto;
import ro.msg.learning.shop.dto.ProductIdAndQuantityDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.strategy.LocationSelectionStrategy;
import ro.msg.learning.shop.strategy.SingleLocationSelectionStrategy;

import java.util.*;


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
    private SingleLocationSelectionStrategy singleLocationSelectionStrategy;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;

    public Order createOrder(OrderCreateDto orderCreateDto) {

        List<Location> locationsList = singleLocationSelectionStrategy.selectLocations(convertToProductList(orderCreateDto));

        Order order = new Order();
        Customer customer = customerRepository.findById(orderCreateDto.getIdCustomer()).orElse(null);
        if(customer==null)
        {
            throw new ResourceNotFoundException(CUSTOMER_NOT_FOUND);
        }
        order.setCustomer(customer);
        order.setCreatedAt(orderCreateDto.getLocalDateTime());
        order.setAddressCountry(orderCreateDto.getCountry());
        order.setAddressCity(orderCreateDto.getCity());
        order.setAddressCounty(orderCreateDto.getCounty());
        order.setAddressStreet(orderCreateDto.getStreetAddress());
        boolean ok=false;
        for(Location location:locationsList)
        {
            if(stockService.hasSufficientStock(location,orderCreateDto.getProductList()))
            {
                stockService.updateStockQuantities(location, orderCreateDto.getProductList());
                ok=true;
                break;
            }
        }

        if(!ok) {
            throw new ResourceNotFoundException(NO_LOCATION_WITH_SUFFICIENT_STOCK_FOUND);
        }

        return orderRepository.save(order);
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
