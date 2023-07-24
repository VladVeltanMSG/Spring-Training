package ro.msg.learning.shop.strategy;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.domain.Product;
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
import static ro.msg.learning.shop.service.StockService.PRODUCT_NOT_FOUND_WITH_ID;


@AllArgsConstructor
public class MostAbundantSelectionStrategy implements LocationSelectionStrategy {

    public static final String INSUFFICIENT_STOCK_FOR_PRODUCT_IN_LOCATION = "Insufficient stock for product in location";
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order selectLocations(OrderCreateDto orderCreateDto) {
        List<Object[]> locationsMostAbundant = stockRepository.findLocationsMostAbundant(convertToProductList(orderCreateDto));
        for (Object[] obj : locationsMostAbundant) {
            Location location = (Location) obj[0];
            Product product = (Product) obj[1];
            int productQuantity = 0;
            for (ProductIdAndQuantityDto productIdAndQuantityDto : orderCreateDto.getProductList()) {
                if (product.getId() == productIdAndQuantityDto.getProductId()) {
                    productQuantity = productIdAndQuantityDto.getQuantity();
                    break;
                }
            }
            ProductIdAndQuantityDto currentProductToUpdateStock = new ProductIdAndQuantityDto(product.getId(), productQuantity);
            List<ProductIdAndQuantityDto> singleLocationList = new ArrayList<>();
            singleLocationList.add(currentProductToUpdateStock);
            if (stockService.hasSufficientStock(location, singleLocationList))
            {
                stockService.updateStockQuantities(location, singleLocationList);
            }
            else
            {
                throw new ResourceNotFoundException(INSUFFICIENT_STOCK_FOR_PRODUCT_IN_LOCATION);
            }
        }
        Order order = new Order();
        Customer customer = customerRepository.findById(orderCreateDto.getIdCustomer()).orElse(null);
        if (customer == null) {
            throw new ResourceNotFoundException(CUSTOMER_NOT_FOUND);
        }
        order.setCustomer(customer);
        order.setCreatedAt(orderCreateDto.getLocalDateTime());
        order.setAddressCountry(orderCreateDto.getCountry());
        order.setAddressCity(orderCreateDto.getCity());
        order.setAddressCounty(orderCreateDto.getCounty());
        order.setAddressStreet(orderCreateDto.getStreetAddress());

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

