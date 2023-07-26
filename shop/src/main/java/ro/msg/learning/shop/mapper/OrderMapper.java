package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.dto.OrderCreateDto;
import ro.msg.learning.shop.dto.OrderDto;


@Component
public class OrderMapper {

    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .localDateTime(order.getCreatedAt())
                .country(order.getAddressCountry())
                .city(order.getAddressCity())
                .county(order.getAddressCounty())
                .streetAdress(order.getAddressStreet())
                .build();
    }

    public Order orderCreateDtoToOrder(OrderCreateDto orderCreateDto, Customer customer) {
        return Order.builder().customer(customer)
                .createdAt(orderCreateDto.getLocalDateTime())
                .addressCountry(orderCreateDto.getCountry())
                .addressCity(orderCreateDto.getCity())
                .addressCounty(orderCreateDto.getCounty())
                .addressStreet(orderCreateDto.getStreetAddress())
                .build();
    }

}
