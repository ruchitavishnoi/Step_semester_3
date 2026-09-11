public class FareSplitter {
    private final String tripId;
    private final double totalFare;
    private final int passengerCount;

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0 || passengerCount <= 0) {
            throw new IllegalArgumentException("Invalid fare or passenger count.");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public double[] fareBreakdown() {
        double[] breakdown = new double[passengerCount];

        double baseShare = Math.floor((totalFare / passengerCount) * 100.0) / 100.0;
        
        double currentTotal = 0.0;
        for (int i = 0; i < passengerCount; i++) {
            breakdown[i] = baseShare;
            currentTotal += baseShare;
        }

        double leftover = Math.round((totalFare - currentTotal) * 100.0) / 100.0;
        breakdown[passengerCount - 1] = Math.round((breakdown[passengerCount - 1] + leftover) * 100.0) / 100.0;

        return breakdown;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
}
