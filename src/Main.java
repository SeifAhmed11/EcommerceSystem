//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Fawry E-commerce System Demo ===\n");
        
        // Create products
        Cheese cheese = new Cheese("Cheese", 100, 10, LocalDate.now().plusDays(30), 0.4);
        Biscuits biscuits = new Biscuits("Biscuits", 150, 5, LocalDate.now().plusDays(15), 0.7);
        TV tv = new TV("TV", 5000, 3, 15.0);
        Mobile mobile = new Mobile("Mobile", 3000, 8);
        ScratchCard scratchCard = new ScratchCard("ScratchCard", 50, 20);
        
        // Create customer
        Customer customer = new Customer("Ahmed", 1000);
        
        // Create cart
        Cart cart = new Cart();
        
        // Test Case 1: Normal checkout with shippable and non-shippable items
        System.out.println("Test Case 1: Normal Checkout");
        System.out.println("============================");
        try {
            cart.addProduct(cheese, 2);
            cart.addProduct(biscuits, 1);
            cart.addProduct(scratchCard, 1);
            
            CheckoutService checkoutService = new CheckoutService();
            checkoutService.checkout(customer, cart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test Case 2: Empty cart
        System.out.println("Test Case 2: Empty Cart");
        System.out.println("=======================");
        Cart emptyCart = new Cart();
        try {
            CheckoutService checkoutService = new CheckoutService();
            checkoutService.checkout(customer, emptyCart);
        } catch (EmptyCartException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test Case 3: Insufficient balance
        System.out.println("Test Case 3: Insufficient Balance");
        System.out.println("=================================");
        Customer poorCustomer = new Customer("Poor Customer", 100);
        Cart expensiveCart = new Cart();
        try {
            expensiveCart.addProduct(tv, 1);
            CheckoutService checkoutService = new CheckoutService();
            checkoutService.checkout(poorCustomer, expensiveCart);
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test Case 4: Out of stock
        System.out.println("Test Case 4: Out of Stock");
        System.out.println("=========================");
        Cart outOfStockCart = new Cart();
        try {
            outOfStockCart.addProduct(cheese, 15); // Only 10 available
            CheckoutService checkoutService = new CheckoutService();
            checkoutService.checkout(customer, outOfStockCart);
        } catch (OutOfStockException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test Case 5: Expired product
        System.out.println("Test Case 5: Expired Product");
        System.out.println("============================");
        Cheese expiredCheese = new Cheese("Expired Cheese", 50, 5, LocalDate.now().minusDays(1), 0.3);
        Cart expiredCart = new Cart();
        try {
            expiredCart.addProduct(expiredCheese, 1);
            CheckoutService checkoutService = new CheckoutService();
            checkoutService.checkout(customer, expiredCart);
        } catch (ExpiredProductException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test Case 6: Multiple items with shipping
        System.out.println("Test Case 6: Multiple Items with Shipping");
        System.out.println("=========================================");
        Customer richCustomer = new Customer("Rich Customer", 10000);
        Cart shippingCart = new Cart();
        try {
            shippingCart.addProduct(cheese, 2);
            shippingCart.addProduct(biscuits, 1);
            shippingCart.addProduct(tv, 1);
            shippingCart.addProduct(mobile, 1);
            
            CheckoutService checkoutService = new CheckoutService();
            checkoutService.checkout(richCustomer, shippingCart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test Case 7: Non-shippable items only
        System.out.println("Test Case 7: Non-shippable Items Only");
        System.out.println("=====================================");
        Cart nonShippingCart = new Cart();
        try {
            nonShippingCart.addProduct(mobile, 1);
            nonShippingCart.addProduct(scratchCard, 3);
            
            CheckoutService checkoutService = new CheckoutService();
            checkoutService.checkout(customer, nonShippingCart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Demo Completed Successfully ===");
    }
}