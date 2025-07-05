import java.util.ArrayList;
import java.util.List;

// Shipping service
class ShippingService {
    public void shipItems(List<Shippable> items) {
        if (items.isEmpty()) {
            return;
        }
        
        System.out.println("** Shipment notice **");
        double totalWeight = 0.0;
        
        for (Shippable item : items) {
            double weightInGrams = item.getWeight() * 1000; // Convert kg to grams
            System.out.printf("%dx %s %.0fg%n", 1, item.getName(), weightInGrams);
            totalWeight += item.getWeight();
        }
        
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
}

// Checkout service
class CheckoutService {
    private ShippingService shippingService;
    
    public CheckoutService() {
        this.shippingService = new ShippingService();
    }
    
    public void checkout(Customer customer, Cart cart) {
        // Validate cart is not empty
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cart is empty. Cannot proceed with checkout.");
        }
        
        // Validate products
        List<Shippable> shippableItems = new ArrayList<>();
        double subtotal = 0.0;
        
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            
            // Check if product is out of stock
            if (quantity > product.getQuantity()) {
                throw new OutOfStockException(
                    String.format("Product %s is out of stock. Available: %d, Requested: %d", 
                    product.getName(), product.getQuantity(), quantity)
                );
            }
            
            // Check if product is expired
            if (product.isExpired()) {
                throw new ExpiredProductException(
                    String.format("Product %s is expired and cannot be purchased.", product.getName())
                );
            }
            
            // Calculate subtotal
            subtotal += product.getPrice() * quantity;
            
            // Add to shippable items if applicable
            if (product.isShippable() && product instanceof Shippable) {
                for (int i = 0; i < quantity; i++) {
                    shippableItems.add((Shippable) product);
                }
            }
        }
        
        // Calculate shipping fees (30 for shippable items)
        double shippingFees = shippableItems.isEmpty() ? 0.0 : 30.0;
        double totalAmount = subtotal + shippingFees;
        
        // Check if customer has sufficient balance
        if (totalAmount > customer.getBalance()) {
            throw new InsufficientBalanceException(
                String.format("Insufficient balance. Required: %.2f, Available: %.2f", 
                totalAmount, customer.getBalance())
            );
        }
        
        // Process shipping
        shippingService.shipItems(shippableItems);
        
        // Print receipt
        printReceipt(cart, subtotal, shippingFees, totalAmount);
        
        // Update customer balance
        customer.setBalance(customer.getBalance() - totalAmount);
        
        // Update product quantities
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }
        
        System.out.printf("Customer balance after payment: %.2f%n", customer.getBalance());
    }
    
    private void printReceipt(Cart cart, double subtotal, double shippingFees, double totalAmount) {
        System.out.println("** Checkout receipt **");
        
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            System.out.printf("%dx %s %.0f%n", item.getQuantity(), product.getName(), product.getPrice());
        }
        
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingFees);
        System.out.printf("Amount %.0f%n", totalAmount);
    }
} 