package ro.msg.learning.shop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.StockService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LocationSelectionStrategyTests {

	@Test
	void contextLoads() {
	}
	@Mock
	private StockService stockService;
	@Mock
	private StockRepository stockRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private ProductService productService;

}
