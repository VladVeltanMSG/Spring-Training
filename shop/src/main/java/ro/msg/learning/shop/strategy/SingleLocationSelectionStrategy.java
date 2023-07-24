package ro.msg.learning.shop.strategy;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.dto.OrderCreateDto;
import ro.msg.learning.shop.dto.ProductIdAndQuantityDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.StockService;

import java.util.ArrayList;
import java.util.List;

import static ro.msg.learning.shop.service.OrderService.CUSTOMER_NOT_FOUND;
import static ro.msg.learning.shop.service.OrderService.NO_LOCATION_WITH_SUFFICIENT_STOCK_FOUND;
import static ro.msg.learning.shop.service.StockService.PRODUCT_NOT_FOUND_WITH_ID;


@AllArgsConstructor
public class SingleLocationSelectionStrategy implements LocationSelectionStrategy {

    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Override
    public Order selectLocations(OrderCreateDto orderCreateDto) {


        List<Location> locationsList =stockRepository.findLocationsByProducts(convertToProductList(orderCreateDto), convertToProductList(orderCreateDto).size());

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

