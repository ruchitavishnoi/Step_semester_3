import java.util.Arrays;

public class RacePenaltyManager {

    public static class RaceEntry {
        private String bibNumber;
        private double entryFee;
        private double paidAmount;
        private double[] lateFeeHistory = new double[10];
        private int historyCount = 0;

        public RaceEntry(String bibNumber, double entryFee) {
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.paidAmount = 0.0;
        }

        public void pay(double amount) {
            this.paidAmount += amount;
        }

        public double getBalanceDue() {
            return this.entryFee - this.paidAmount;
        }

        protected void applyLateFee(double amount) {
            this.entryFee += amount;
            if (historyCount < 10) {
                lateFeeHistory[historyCount++] = amount;
            }
        }

        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, historyCount);
        }
    }

    public static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println("Balance Due: " + r.getBalanceDue()); // 90.0

        double[] history = r.getLateFeeHistory();
        System.out.println("History: " + Arrays.toString(history)); // [40.0]

        history[0] = 999;
        System.out.println("After Tampering Attempt: " + Arrays.toString(r.getLateFeeHistory())); // [40.0]
    }
}
