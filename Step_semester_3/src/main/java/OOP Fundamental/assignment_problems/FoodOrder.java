public class FoodOrder {
    private String studentName;
    private String dishName;
    private boolean isDelivered = false;

    private FoodOrder() {}

    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name.");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name.");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
    }

    public void markDelivered() {
        if (isDelivered) {
            System.out.println("Warning: Order for " + studentName + " was ALREADY delivered! Potential double-serve.");
        } else {
            this.isDelivered = true;
            System.out.println("Order for " + studentName + " delivered successfully.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;

        if (rawOrders != null) {
            for (String[] order : rawOrders) {
                if (order == null || order.length < 2) {
                    rejected++;
                    continue;
                }
                try {
                    new FoodOrder(order[0], order[1]);
                    valid++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };

        processBatch(rawOrders);
    }
}
