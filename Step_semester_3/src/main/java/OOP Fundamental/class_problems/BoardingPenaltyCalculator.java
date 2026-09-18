public final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty percent cannot be negative.");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Ticket fare and minutes late cannot be negative.");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        int tier1Minutes = Math.min(minutesLate, 5);
        int tier2Minutes = Math.max(0, Math.min(minutesLate - 5, 10));
        int tier3Minutes = Math.max(0, minutesLate - 15);

        double tieredPercent = (tier1Minutes * 0.005) + (tier2Minutes * 0.010) + (tier3Minutes * 0.020);
        double tieredPenalty = ticketFare * tieredPercent;

        double floorPenalty = ticketFare * (minimumPenaltyPercent / 100.0);

        return Math.max(tieredPenalty, floorPenalty);
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0);

        System.out.println("Rs " + calc.calculatePenalty(1000, 0));
        System.out.println("Rs " + calc.calculatePenalty(1000, 1));
        System.out.println("Rs " + calc.calculatePenalty(1000, 16));
    }
}
