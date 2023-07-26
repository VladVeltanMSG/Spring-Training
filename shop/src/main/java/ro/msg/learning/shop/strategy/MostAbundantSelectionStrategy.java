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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ro.msg.learning.shop.service.OrderService.CUSTOMER_NOT_FOUND;
import static ro.msg.learning.shop.service.StockService.PRODUCT_NOT_FOUND_WITH_ID;


public class MostAbundantSelectionStrategy implements LocationSelectionStrategy {

    public static final String INSUFFICIENT_STOCK_FOR_PRODUCT_IN_LOCATION = "Insufficient stock for product in location";
    public static final String COULD_NOT_FOUND_PRODUCT_IN_PRODUCT_ID_AND_QUANTITY_DTO_LIST = "Could not found product in productIdAndQuantityDtoList";
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
        List<Object[]> locationsMostAbundant = stockService.findLocationsMostAbundant(convertToProductList(orderCreateDto));

        for (Object[] obj : locationsMostAbundant) {
            Location location = (Location) obj[0];
            Product product = (Product) obj[1];

            int productQuantity = findQuantityOfGivenProductInProductIdAndQuantityList(product, orderCreateDto.getProductList());

            ProductIdAndQuantityDto currentProductToUpdateStock = new ProductIdAndQuantityDto(product.getId(), productQuantity);

            List<ProductIdAndQuantityDto> singleLocationList = new ArrayList<>();
            singleLocationList.add(currentProductToUpdateStock);

            if (stockService.hasSufficientStock(location, singleLocationList)) {
                stockService.updateStockQuantities(location, singleLocationList);
            } else {
                throw new ResourceNotFoundException(INSUFFICIENT_STOCK_FOR_PRODUCT_IN_LOCATION);
            }
        }
        Customer customer = customerService.findById(orderCreateDto.getIdCustomer()).orElseThrow(() -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));

        return orderMapper.orderCreateDtoToOrder(orderCreateDto, customer);
    }

    public int findQuantityOfGivenProductInProductIdAndQuantityList(Product product, List<ProductIdAndQuantityDto> productIdAndQuantityDtoList) {
        for (ProductIdAndQuantityDto productIdAndQuantityDto : productIdAndQuantityDtoList) {
            if (product.getId().equals(productIdAndQuantityDto.getProductId())) {
                return productIdAndQuantityDto.getQuantity();
            }
        }
        throw new ResourceNotFoundException(COULD_NOT_FOUND_PRODUCT_IN_PRODUCT_ID_AND_QUANTITY_DTO_LIST);
    }


    public List<Product> convertToProductList(OrderCreateDto orderCreateDto) {
        List<ProductIdAndQuantityDto> productIdAndQuantityList = orderCreateDto.getProductList();

        List<Product> productList = new ArrayList<>();
        for (ProductIdAndQuantityDto productIdAndQuantityDto : productIdAndQuantityList) {
            Optional<Product> product = productService.findById(productIdAndQuantityDto.getProductId());
            if (product.isEmpty()) {
                throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID + productIdAndQuantityDto.getProductId());
            }
            productList.add(product.get());
        }
        return productList;
    }
}

