import java.util.ArrayList;
import java.util.List;

interface IPaymentMethod {
    boolean processPayment(double amount);
    String getMethodName();
}

class CreditCardPayment implements IPaymentMethod {
    private boolean simulateSuccess;

    public CreditCardPayment(boolean simulateSuccess) {
        this.simulateSuccess = simulateSuccess;
    }

    @Override
    public boolean processPayment(double amount) {
        return simulateSuccess;
    }

    @Override
    public String getMethodName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    private boolean simulateSuccess;

    public DigitalWalletPayment(boolean simulateSuccess) {
        this.simulateSuccess = simulateSuccess;
    }

    @Override
    public boolean processPayment(double amount) {
        return simulateSuccess;
    }

    @Override
    public String getMethodName() {
        return "Digital Wallet";
    }
}

class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class LineItem {
    private FoodItem foodItem;
    private int quantity;

    public LineItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return foodItem.getPrice() * quantity;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void notifyCustomer(String message) {
        System.out.println("Notification: " + message);
    }
}

class Restaurant {
    private String name;

    public Restaurant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID
}

class Order {
    private static int orderCounter = 123;
    private int orderId;
    private Customer customer;
    private Restaurant restaurant;
    private List<LineItem> lineItems;
    private OrderStatus status;

    public Order(Customer customer, Restaurant restaurant) {
        this.orderId = orderCounter++;
        this.customer = customer;
        this.restaurant = restaurant;
        this.lineItems = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    public int getOrderId() {
        return orderId;
    }

    public void addItem(FoodItem item, int quantity) {
        lineItems.add(new LineItem(item, quantity));
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public double calculateTotal() {
        double total = 0;
        for (LineItem item : lineItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    public boolean placeOrder(IPaymentMethod paymentMethod) {
        if (lineItems.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return false;
        }

        boolean success = paymentMethod.processPayment(calculateTotal());
        if (success) {
            this.status = OrderStatus.PAID;
            System.out.println("Order placed successfully. Payment via " + paymentMethod.getMethodName() + " successful. Order status: Paid.");
            customer.notifyCustomer("Order #" + orderId + " placed and paid.");
            return true;
        } else {
            System.out.println("Order placed.");
            this.status = OrderStatus.PENDING_PAYMENT;
            System.out.println("Payment via " + paymentMethod.getMethodName() + " failed. Order status: Pending Payment.");
            customer.notifyCustomer("Order #" + orderId + " placed, awaiting payment.");
            return false;
        }
    }
}

public class FoodOrder {
    public static void main(String[] args) {
        Customer customer = new Customer("C01", "John");
        Restaurant restaurant = new Restaurant("Tasty Bites");

        FoodItem pizza = new FoodItem("Pizza", 10.00);
        FoodItem soda = new FoodItem("Soda", 2.50);
        FoodItem burger = new FoodItem("Burger", 8.00);

        Order order1 = new Order(customer, restaurant);
        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);
        System.out.println("Order created. Added Pizza (Qty 2), Soda (Qty 1).");

        Order emptyOrder = new Order(customer, restaurant);
        emptyOrder.placeOrder(new CreditCardPayment(true));

        order1.placeOrder(new CreditCardPayment(true));

        Order order2 = new Order(customer, restaurant);
        order2.addItem(burger, 1);
        order2.placeOrder(new DigitalWalletPayment(false));
    }
}
