package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.domain.StockId;
import ro.msg.learning.shop.dto.StockCreateDto;
import ro.msg.learning.shop.dto.StockGetDto;
import ro.msg.learning.shop.service.StockService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockGetDto>> getAllStocks() {
        List<StockGetDto> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{idProduct}/{idLocation}")
    public ResponseEntity<StockGetDto> getStockById(@PathVariable UUID idProduct,@PathVariable UUID idLocation) {
        StockGetDto stock = stockService.getStockById(StockId.builder().product(idProduct).location(idLocation).build());
        return ResponseEntity.ok(stock);
    }

    @PostMapping
    public ResponseEntity<StockGetDto> createStock(@RequestBody StockCreateDto stockCreateDto) {
        StockGetDto createdStock = stockService.createStock(stockCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    @PutMapping("/{idProduct}/{idLocation}")
    public ResponseEntity<StockGetDto> updateStock(@PathVariable UUID idProduct,@PathVariable UUID idLocation, @RequestBody StockCreateDto stockCreateDto) {
        StockGetDto updatedStock = stockService.updateStock(StockId.builder().product(idProduct).location(idLocation).build(), stockCreateDto);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{idProduct}/{idLocation}")
    public ResponseEntity<Void> deleteStock(@PathVariable UUID idProduct,@PathVariable UUID idLocation) {
        stockService.deleteStock(StockId.builder().product(idProduct).location(idLocation).build());
        return ResponseEntity.noContent().build();
    }
}
