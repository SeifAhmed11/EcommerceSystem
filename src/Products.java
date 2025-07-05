import java.time.LocalDate;

// Abstract base class for all products
abstract class Product {
    protected String name;
    protected double price;
    protected int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public abstract boolean isExpired();
    public abstract boolean isShippable();
}

// Interface for shippable products
interface Shippable {
    String getName();
    double getWeight();
}

// Abstract class for expirable products
abstract class ExpirableProduct extends Product {
    protected LocalDate expiryDate;

    public ExpirableProduct(String name, double price, int quantity, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
}

// Abstract class for non-expirable products
abstract class NonExpirableProduct extends Product {
    public NonExpirableProduct(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public boolean isExpired() {
        return false;
    }
}

// Concrete product classes
class Cheese extends ExpirableProduct implements Shippable {
    private double weight; // in kg

    public Cheese(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity, expiryDate);
        this.weight = weight;
    }

    @Override
    public boolean isShippable() {
        return true;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

class Biscuits extends ExpirableProduct implements Shippable {
    private double weight; // in kg

    public Biscuits(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity, expiryDate);
        this.weight = weight;
    }

    @Override
    public boolean isShippable() {
        return true;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

class TV extends NonExpirableProduct implements Shippable {
    private double weight; // in kg

    public TV(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public boolean isShippable() {
        return true;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

class Mobile extends NonExpirableProduct {
    public Mobile(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public boolean isShippable() {
        return false;
    }
}

class ScratchCard extends NonExpirableProduct {
    public ScratchCard(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public boolean isShippable() {
        return false;
    }
} 