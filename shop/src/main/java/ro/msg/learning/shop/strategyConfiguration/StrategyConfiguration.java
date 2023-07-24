package ro.msg.learning.shop.strategyConfiguration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.strategy.LocationSelectionStrategy;
import ro.msg.learning.shop.strategy.MostAbundantSelectionStrategy;
import ro.msg.learning.shop.strategy.SingleLocationSelectionStrategy;


@Configuration
public class StrategyConfiguration {
    public enum Strategy{
        SINGLE_STRATEGY,MOST_ABUNDANT
    }
    @Value("${strategy}")
    private Strategy strategy;
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

    @Bean
    public LocationSelectionStrategy selectStrategy()
    {
        if(strategy.equals(Strategy.MOST_ABUNDANT))
        {
            return new MostAbundantSelectionStrategy(stockService,stockRepository,productService,customerRepository,orderRepository);
        }
        else
        {
            return new SingleLocationSelectionStrategy(stockService,stockRepository,customerRepository,orderRepository,productService);
        }
    }

}
