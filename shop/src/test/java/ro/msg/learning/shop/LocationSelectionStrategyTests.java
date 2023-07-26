package ro.msg.learning.shop;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.dto.OrderCreateDto;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.strategy.LocationSelectionStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LocationSelectionStrategyTests {


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
    @Mock
    private LocationSelectionStrategy locationSelectionStrategy;
    @InjectMocks
    private OrderService orderService;

    @Before("")
    public void init() {
        ProductCategory productCategory = new ProductCategory("Tech", "Chestii de Tech");
        Product product = new Product("Del 2000", "Laptop de birou", 7300, 1200, productCategory, "Emag", "del.com");
        Location location = new Location("Zalau Location", "Romania", "Zalau", "Salaj", "Str Avram Iancu 3");
        Location location2 = new Location("Cluj Location", "Romania", "Cluj-Napoca", "Cluj", "Str Brassai 7");
        Customer customer = new Customer("Vlad", "Veltan", "Vladut", "alabala", "vladut@yahoo.com");
        Stock stock = new Stock(product, location, 4);
        Stock stock2 = new Stock(product, location2, 19);

        //OrderCreateDto orderCreateDto=new OrderCreateDto(customer.getId(),"2023-07-19T15:30:45","Romania","Zalau","Salaj","Avram Inacu 3",)

        List<Location> locationList = new ArrayList<>();
        locationList.add(location);
        locationList.add(location2);

        //when(locationSelectionStrategy.selectLocations(OrderCreateDto)).thenReturn();
    }




}
