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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ro.msg.learning.shop.service.OrderService.CUSTOMER_NOT_FOUND;
import static ro.msg.learning.shop.service.OrderService.NO_LOCATION_WITH_SUFFICIENT_STOCK_FOUND;

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
    public Order createOrder(OrderCreateDto orderCreateDto) {
        List<Location> locationsList = stockService.findLocationsByProducts(convertToProductList(orderCreateDto), convertToProductList(orderCreateDto).size());

        Customer customer = customerService.findById(orderCreateDto.getIdCustomer()).orElseThrow(() -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
        boolean foundSuitableLocation=false;

        for (Location location : locationsList) {
            if (stockService.hasSufficientStock(location, orderCreateDto.getProductList())) {
                stockService.updateStockQuantities(location, orderCreateDto.getProductList());
                foundSuitableLocation=true;
            }
        }
        if (!foundSuitableLocation) {
            throw new ResourceNotFoundException(NO_LOCATION_WITH_SUFFICIENT_STOCK_FOUND);
        }

        return orderMapper.orderCreateDtoToOrder(orderCreateDto, customer);

    }

    public List<Product> convertToProductList(OrderCreateDto orderCreateDto) {
        List<ProductIdAndQuantityDto> productIdAndQuantityList = orderCreateDto.getProductList();

        return productIdAndQuantityList.stream()
                .map(ProductIdAndQuantityDto::getProductId)
                .map(productService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}

