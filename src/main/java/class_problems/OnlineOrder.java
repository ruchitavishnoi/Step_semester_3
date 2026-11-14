class Order {
    private LineItem[] items;
    private int count;

    public Order(int capacity) {
        this.items = new LineItem[capacity];
        this.count = 0;
    }

    public void addItem(String productName, int quantity, double unitPrice) {
        if (count < items.length) {
            items[count++] = new LineItem(productName, quantity, unitPrice);
        }
    }

    public Cursor getCursor() {
        return new Cursor();
    }

    public static class LineItem {
        String productName;
        int quantity;
        double unitPrice;

        public LineItem(String productName, int quantity, double unitPrice) {
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public double total() {
            return quantity * unitPrice;
        }
    }

    public class Cursor {
        private int index = 0;

        public boolean hasNext() {
            return index < count;
        }

        public LineItem next() {
            if (!hasNext()) {
                return null;
            }
            return items[index++];
        }

        public void reset() {
            index = 0;
        }
    }
}

class Solution {
    public static void main(String[] args) {
        Order order = new Order(100);
        order.addItem("Pen", 3, 10);
        order.addItem("Book", 2, 250);
        order.addItem("Bag", 1, 700);

        Order.Cursor cursor = order.getCursor();
        double orderTotal = 0;

        while (cursor.hasNext()) {
            Order.LineItem item = cursor.next();
            double itemTotal = item.total();
            orderTotal += itemTotal;
            System.out.println(item.productName + " " + (int)itemTotal);
        }

        System.out.println("Total " + (int)orderTotal);
        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");
    }
}
