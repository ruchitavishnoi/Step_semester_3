abstract class Ride {
    protected double distance;

    public Ride(double distance) {
        this.distance = distance;
    }

    public abstract double calculateFare();
}

class Auto extends Ride {
    public Auto(double distance) {
        super(distance);
    }

    @Override
    public double calculateFare() {
        return 30 + (15 * distance);
    }
}

class Cab extends Ride {
    private double surgeFactor;

    public Cab(double distance, double surgeFactor) {
        super(distance);
        this.surgeFactor = surgeFactor;
    }

    @Override
    public double calculateFare() {
        return (50 + (20 * distance)) * surgeFactor;
    }
}

class Bike extends Ride {
    private int waitingMinutes;

    public Bike(double distance, int waitingMinutes) {
        super(distance);
        this.waitingMinutes = waitingMinutes;
    }

    @Override
    public double calculateFare() {
        return 20 + (10 * distance) + (2 * waitingMinutes);
    }
}

class RideFareEngine {
    public static void main(String[] args) {
        Ride[] rides = new Ride[3];
        rides[0] = new Auto(4);
        rides[1] = new Cab(6, 1.5);
        rides[2] = new Bike(3, 5);

        double totalFare = 0;

        for (Ride ride : rides) {
            double fare = ride.calculateFare();
            totalFare += fare;
            
            String type = "";
            if (ride instanceof Auto) type = "Auto";
            else if (ride instanceof Cab) type = "Cab";
            else if (ride instanceof Bike) type = "Bike";
            
            System.out.println(type + ": " + (int)fare);
        }

        System.out.println("Total: " + (int)totalFare);
    }
}
