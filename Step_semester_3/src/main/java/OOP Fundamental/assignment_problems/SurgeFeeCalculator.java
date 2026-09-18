public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percent cannot be negative.");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay minutes cannot be negative.");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        int tier1Minutes = Math.min(delayMinutes, 5);
        int tier2Minutes = Math.max(0, Math.min(delayMinutes - 5, 10));
        int tier3Minutes = Math.max(0, delayMinutes - 15);

        double tieredPercent = (tier1Minutes * 0.005) + (tier2Minutes * 0.010) + (tier3Minutes * 0.020);
        double tieredFee = orderValue * tieredPercent;

        double floorFee = orderValue * (minimumSurgePercent / 100.0);

        return Math.max(tieredFee, floorFee);
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0);

        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16));
    }
}public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percent cannot be negative.");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay minutes cannot be negative.");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        int tier1Minutes = Math.min(delayMinutes, 5);
        int tier2Minutes = Math.max(0, Math.min(delayMinutes - 5, 10));
        int tier3Minutes = Math.max(0, delayMinutes - 15);

        double tieredPercent = (tier1Minutes * 0.005) + (tier2Minutes * 0.010) + (tier3Minutes * 0.020);
        double tieredFee = orderValue * tieredPercent;

        double floorFee = orderValue * (minimumSurgePercent / 100.0);

        return Math.max(tieredFee, floorFee);
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0);

        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16));
    }
} {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percent cannot be negative.");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay minutes cannot be negative.");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        int tier1Minutes = Math.min(delayMinutes, 5);
        int tier2Minutes = Math.max(0, Math.min(delayMinutes - 5, 10));
        int tier3Minutes = Math.max(0, delayMinutes - 15);

        double tieredPercent = (tier1Minutes * 0.005) + (tier2Minutes * 0.010) + (tier3Minutes * 0.020);
        double tieredFee = orderValue * tieredPercent;

        double floorFee = orderValue * (minimumSurgePercent / 100.0);

        return Math.max(tieredFee, floorFee);
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0);

        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16));
    }
}
