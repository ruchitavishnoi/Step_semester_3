import java.util.Arrays;

public class TicketPenaltyAudit {

    public static class EventTicket {
        private String attendeeId;
        private double basePrice;
        private double paidAmount;
        private double[] lateFeeHistory;
        private int lateFeeCount;

        public EventTicket(double basePrice) {
            this("DEFAULT", basePrice);
        }

        public EventTicket(String attendeeId, double basePrice) {
            this.attendeeId = attendeeId;
            this.basePrice = basePrice;
            this.paidAmount = 0.0;
            this.lateFeeHistory = new double[10];
            this.lateFeeCount = 0;
        }

        public void pay(double amount) {
            this.paidAmount += amount;
        }

        protected void applyLateFee(double amount) {
            if (lateFeeCount < lateFeeHistory.length) {
                lateFeeHistory[lateFeeCount++] = amount;
            }
        }

        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }

        public double getBalanceDue() {
            double totalLateFees = 0.0;
            for (int i = 0; i < lateFeeCount; i++) {
                totalLateFees += lateFeeHistory[i];
            }
            return (basePrice + totalLateFees) - paidAmount;
        }
    }

    public static class WorkshopTicket extends EventTicket {

        public WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        public WorkshopTicket(String attendeeId, double basePrice) {
            super(attendeeId, basePrice);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history));

        history[0] = 999;
        System.out.println(Arrays.toString(w.getLateFeeHistory()));
    }
}
