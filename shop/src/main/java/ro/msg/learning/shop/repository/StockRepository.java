package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.domain.StockId;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, StockId> {

    @Query(" SELECT location" +
            " FROM Stock" +
            " WHERE product IN :products" +
            " GROUP BY location" +
            " HAVING COUNT(DISTINCT product) =:productsCount")
    List<Location> findLocationsByProducts(@Param("products") List<Product> products, @Param("productsCount") Integer productsCount);

    @Query("SELECT s.location, s.product, s.quantity " +
            "FROM Stock s " +
            "WHERE s.product IN :products AND (s.product, s.quantity) IN (" +
            "   SELECT s2.product, MAX(s2.quantity) " +
            "   FROM Stock s2 " +
            "   WHERE s2.product IN :products " +
            "   GROUP BY s2.product" +
            ")")
    List<Object[]> findLocationsMostAbundant(@Param("products") List<Product> products);


    Stock findByLocationAndProduct(Location location, Product product);
}
