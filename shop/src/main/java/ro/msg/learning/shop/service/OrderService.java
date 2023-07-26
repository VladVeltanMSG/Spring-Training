package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderCreateDto;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.strategy.LocationSelectionStrategy;

@Service
public class OrderService {
    public static final String CUSTOMER_NOT_FOUND = "Customer not found!";
    public static final String NO_LOCATION_WITH_SUFFICIENT_STOCK_FOUND = "No location with sufficient stock found";
    @Autowired
    private LocationSelectionStrategy locationSelectionStrategy;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;

    public OrderDto createOrder(OrderCreateDto orderCreateDto) {
        orderRepository.save(locationSelectionStrategy.createOrder(orderCreateDto));
        return orderMapper.toDto(locationSelectionStrategy.createOrder(orderCreateDto));
    }
}
