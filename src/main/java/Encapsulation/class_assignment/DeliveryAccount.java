public class DeliveryAccount {
    private String studentId;
    private double orderValue;

    private static final double DEFAULT_ORDER_VALUE = 0.0;
    private static final double MINIMUM_SURGE_PERCENT = 1.0;

    static {
    }

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, DEFAULT_ORDER_VALUE);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        if (delayMinutes <= 0 || orderValue <= 0) {
            return 0.0;
        }

        int tier1 = Math.min(delayMinutes, 5);
        int tier2 = Math.max(0, Math.min(delayMinutes - 5, 10));
        int tier3 = Math.max(0, delayMinutes - 15);

        double tieredPercent = (tier1 * 0.005) + (tier2 * 0.010) + (tier3 * 0.020);
        double tieredFee = orderValue * tieredPercent;
        double floorFee = orderValue * (MINIMUM_SURGE_PERCENT / 100.0);

        return Math.max(tieredFee, floorFee);
    }

    public static void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) return;
        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length) return;

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFees = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            DeliveryAccount account = accounts[i];
            if (account == null) {
                nullSkipped++;
                continue;
            }

            double surgeFee = account.calculateSurgeFee(delayMinutesArray[i]);
            if (account instanceof Premium) {
                premiumCount++;
                surgeFee *= 0.5;
            } else {
                regularCount++;
            }

            processed++;
            grandTotalSurgeFees += surgeFee;
        }

        System.out.printf("%d processed | %d null skipped | %d premium | %d regular | grand total surge fees = %.1f\n",
                processed, nullSkipped, premiumCount, regularCount, grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new Premium("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}

class Premium extends DeliveryAccount {
    public Premium(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public Premium(String studentId) {
        super(studentId);
    }
}
