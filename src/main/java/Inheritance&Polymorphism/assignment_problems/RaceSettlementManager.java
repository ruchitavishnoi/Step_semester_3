import java.util.Arrays;

public class RaceSettlementManager {

    public static class RaceEntry {
        private static int bibCounter = 0;

        private final String entryCode;
        private String bibNumber;
        private double entryFee;
        private double paidAmount;

        public RaceEntry(String bibNumber, double entryFee) {
            bibCounter++;
            this.entryCode = "ENTRY" + bibCounter;
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.paidAmount = 0.0;
        }

        public void pay(double amount) {
            this.paidAmount += amount;
        }

        public void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        public static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'M') {
                return false;
            }
            if (!Character.isDigit(code.charAt(1)) ||
                !Character.isDigit(code.charAt(2)) ||
                !Character.isDigit(code.charAt(3))) {
                return false;
            }
            return Character.isUpperCase(code.charAt(4));
        }

        public static int getBibCounter() {
            return bibCounter;
        }

        public static String settleNight(RaceEntry[] entries) {
            int processed = 0;
            int nullSkipped = 0;
            int relayCount = 0;
            int individualCount = 0;

            if (entries != null) {
                for (RaceEntry entry : entries) {
                    if (entry == null) {
                        nullSkipped++;
                    } else {
                        processed++;
                        if (entry instanceof RelayTeamEntry) {
                            relayCount++;
                        } else {
                            individualCount++;
                        }
                    }
                }
            }

            return processed + " processed | " + nullSkipped + " null skipped | " +
                   relayCount + " relay | " + individualCount + " individual";
        }

        public String getEntryCode() {
            return entryCode;
        }

        public double getBalanceDue() {
            return entryFee - paidAmount;
        }
    }

    public static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }
    }

    public static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }
    }

    public static void main(String[] args) {
        System.out.println(RaceEntry.isValidDiscountCode("M123A"));
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));
        System.out.println(RaceEntry.isValidDiscountCode("X123A"));

        RunnerEntry eliteEntry = new RunnerEntry("BIB1001", 100.0, "Elite");
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300.0, 4);

        eliteEntry.pay(10, "UPI");

        RaceEntry[] batch = { eliteEntry, null, relayEntry };
        System.out.println(RaceEntry.settleNight(batch));

        System.out.println(RaceEntry.getBibCounter());
    }
}
