public class BusTicketAccount {
    private String bookingId;
    private double ticketFare;

    private static final double DEFAULT_FARE;

    static {
        DEFAULT_FARE = 1000.0;
    }

    public BusTicketAccount(String bookingId, double ticketFare) {
        if (bookingId == null || bookingId.isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty.");
        }
        if (ticketFare < 0) {
            throw new IllegalArgumentException("Ticket fare cannot be negative.");
        }
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, DEFAULT_FARE);
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getTicketFare() {
        return ticketFare;
    }

    public final double calculatePenalty(int minutesLate) {
        if (minutesLate < 0) {
            throw new IllegalArgumentException("Minutes late cannot be negative.");
        }
        if (minutesLate == 0) {
            return 0.0;
        }

        int tier1Minutes = Math.min(minutesLate, 5);
        int tier2Minutes = Math.max(0, Math.min(minutesLate - 5, 10));
        int tier3Minutes = Math.max(0, minutesLate - 15);

        double tieredPercent = (tier1Minutes * 0.005) + (tier2Minutes * 0.010) + (tier3Minutes * 0.020);
        double tieredPenalty = this.ticketFare * tieredPercent;

        double floorPenalty = this.ticketFare * 0.01;

        return Math.max(tieredPenalty, floorPenalty);
    }
}

class SleeperCoachAccount extends BusTicketAccount {
    public SleeperCoachAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }

    public SleeperCoachAccount(String bookingId) {
        super(bookingId);
    }
}

class NightlyReconciliationEngine {

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null) {
            throw new IllegalArgumentException("Input arrays cannot be null.");
        }

        if (accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            throw new IllegalArgumentException("Array lengths do not match. Aborting batch processing to prevent misaligned passenger settlement.");
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalties = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            BusTicketAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            int minutesLate = minutesLateArray[i];
            double penalty = account.calculatePenalty(minutesLate);
            grandTotalPenalties += penalty;

            if (account instanceof SleeperCoachAccount) {
                sleeperCount++;
            } else if (account instanceof BusTicketAccount) {
                regularCount++;
            }
        }

        System.out.printf("%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = Rs %.1f%n",
                processed, nullSkipped, sleeperCount, regularCount, grandTotalPenalties);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
            new SleeperCoachAccount("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };

        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        processBatch(accounts, amounts, minutesLateArray);
    }
}
