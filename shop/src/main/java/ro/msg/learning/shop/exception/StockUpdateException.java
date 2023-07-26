package ro.msg.learning.shop.exception;

public class StockUpdateException extends RuntimeException {
    public StockUpdateException(String message) {
        super(message);
    }
}