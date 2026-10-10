import java.util.ArrayList;
import java.util.List;

interface PricingPlan {
    String getPlanName();
    double calculatePrice(double itemPrice);
}

class DayScholarPlan implements PricingPlan {
    @Override
    public String getPlanName() {
        return "Day Scholar";
    }

    @Override
    public double calculatePrice(double itemPrice) {
        return itemPrice;
    }
}

class HostellerPlan implements PricingPlan {
    @Override
    public String getPlanName() {
        return "Hosteller";
    }

    @Override
    public double calculatePrice(double itemPrice) {
        return itemPrice * 0.90;
    }
}

class StaffPlan implements PricingPlan {
    @Override
    public String getPlanName() {
        return "Staff";
    }

    @Override
    public double calculatePrice(double itemPrice) {
        return itemPrice * 0.80;
    }
}

enum TransactionType {
    TOP_UP,
    PURCHASE,
    REFUND
}

class Transaction {
    private TransactionType type;
    private String description;
    private double amount;
    private boolean refunded;

    public Transaction(TransactionType type, String description, double amount) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.refunded = false;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }
}

class SmartCard {
    private String cardId;
    private PricingPlan pricingPlan;
    private boolean blocked;
    private List<Transaction> transactions;

    public SmartCard(String cardId, PricingPlan pricingPlan) {
        this.cardId = cardId;
        this.pricingPlan = pricingPlan;
        this.blocked = false;
        this.transactions = new ArrayList<>();
    }

    public String getCardId() {
        return cardId;
    }

    public PricingPlan getPricingPlan() {
        return pricingPlan;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void blockCard() {
        this.blocked = true;
    }

    public void unblockCard() {
        this.blocked = false;
    }

    public double getBalance() {
        double balance = 0.0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public boolean topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up failed: Card " + cardId + " is blocked.");
            return false;
        }
        if (amount < 100.0) {
            System.out.println("Top-up failed: Minimum top-up amount is ₹100.00.");
            return false;
        }
        if (getBalance() + amount > 5000.0) {
            System.out.println("Top-up failed: Maximum balance limit of ₹5000.00 exceeded.");
            return false;
        }
        transactions.add(new Transaction(TransactionType.TOP_UP, "Top-up", amount));
        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n", cardId, amount, getBalance());
        return true;
    }

    public boolean purchase(String itemName, double itemPrice) {
        if (blocked) {
            System.out.println("Purchase failed: Card " + cardId + " is blocked.");
            return false;
        }
        double chargedAmount = pricingPlan.calculatePrice(itemPrice);
        double currentBalance = getBalance();
        if (currentBalance < chargedAmount) {
            System.out.printf("Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n", chargedAmount, currentBalance);
            return false;
        }
        transactions.add(new Transaction(TransactionType.PURCHASE, itemName, -chargedAmount));
        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n", itemName, chargedAmount, getBalance());
        return true;
    }

    public boolean refund(String itemName) {
        if (blocked) {
            System.out.println("Refund failed: Card " + cardId + " is blocked.");
            return false;
        }
        Transaction targetTransaction = null;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getType() == TransactionType.PURCHASE && t.getDescription().equalsIgnoreCase(itemName)) {
                targetTransaction = t;
                break;
            }
        }

        if (targetTransaction == null) {
            System.out.println("Refund failed: Purchase record for " + itemName + " not found.");
            return false;
        }

        if (targetTransaction.isRefunded()) {
            System.out.println("Refund rejected: " + itemName + " has already been refunded.");
            return false;
        }

        double refundAmount = Math.abs(targetTransaction.getAmount());
        if (getBalance() + refundAmount > 5000.0) {
            System.out.println("Refund failed: Maximum balance limit of ₹5000.00 exceeded.");
            return false;
        }

        targetTransaction.setRefunded(true);
        transactions.add(new Transaction(TransactionType.REFUND, "Refund: " + itemName, refundAmount));
        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n", refundAmount, itemName, getBalance());
        return true;
    }

    public void printMiniStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mini-statement for ").append(cardId).append(": ");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            double amt = t.getAmount();
            if (i > 0) {
                sb.append(", ");
            }
            if (amt >= 0) {
                sb.append(String.format("+%.2f", amt));
            } else {
                sb.append(String.format("%.2f", amt));
            }
        }
        sb.append(String.format(" = ₹%.2f.", getBalance()));
        System.out.println(sb.toString());
    }
}

public class CampusCard {
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());

        card.topUp(500.00);
        card.purchase("Veg Thali", 120.00);
        card.purchase("Cold Coffee", 60.00);
        card.purchase("Items", 400.00);
        card.refund("Veg Thali");
        card.refund("Veg Thali");
        card.printMiniStatement();
    }
}
