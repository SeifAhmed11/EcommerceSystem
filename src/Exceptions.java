// Custom exception classes
class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) {
        super(message);
    }
}

class ExpiredProductException extends RuntimeException {
    public ExpiredProductException(String message) {
        super(message);
    }
}

class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class EmptyCartException extends RuntimeException {
    public EmptyCartException(String message) {
        super(message);
    }
} 