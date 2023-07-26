package ro.msg.learning.shop.strategyConfiguration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.strategy.LocationSelectionStrategy;
import ro.msg.learning.shop.strategy.MostAbundantSelectionStrategy;
import ro.msg.learning.shop.strategy.SingleLocationSelectionStrategy;


@Configuration
public class StrategyConfiguration {
    @Value("${strategy}")
    private Strategy strategy;

    @Bean
    public LocationSelectionStrategy selectLocationStrategy() {
        if (strategy.equals(Strategy.MOST_ABUNDANT)) {
            return new MostAbundantSelectionStrategy();
        } else {
            return new SingleLocationSelectionStrategy();
        }
    }

    public enum Strategy {
        SINGLE_STRATEGY, MOST_ABUNDANT
    }

}
