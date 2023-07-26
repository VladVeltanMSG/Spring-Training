package ro.msg.learning.shop.strategy;

import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.dto.OrderCreateDto;

public interface LocationSelectionStrategy {
    Order createOrder(OrderCreateDto orderCreateDto);
}

