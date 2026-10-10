abstract class Vehicle {
    private String name;
    private boolean isAvailable;

    public Vehicle(String name) {
        this.name = name;
        this.isAvailable = true;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract double calculateRentalCharge(int days);
}

class StandardCar extends Vehicle {
    private static final double DAILY_RATE = 50.00;

    public StandardCar(String name) {
        super(name);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return DAILY_RATE * days;
    }
}

class LuxuryCar extends Vehicle {
    private static final double DAILY_RATE = 100.00;

    public LuxuryCar(String name) {
        super(name);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return DAILY_RATE * days;
    }
}

class SUV extends Vehicle {
    private static final double DAILY_RATE = 75.00;

    public SUV(String name) {
        super(name);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return DAILY_RATE * days;
    }
}

class Rental {
    private Vehicle vehicle;
    private int days;
    private double totalCharge;

    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateRentalCharge(days);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getDays() {
        return days;
    }

    public double getTotalCharge() {
        return totalCharge;
    }
}

class RentalService {
    public Rental rentVehicle(Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getName() + " is currently not available for rent.");
            return null;
        }
        vehicle.setAvailable(false);
        Rental rental = new Rental(vehicle, days);
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                vehicle.getName(), days, rental.getTotalCharge());
        return rental;
    }

    public void returnVehicle(Vehicle vehicle) {
        vehicle.setAvailable(true);
        System.out.println(vehicle.getName() + " returned. Now available.");
    }
}

public class RentalVehicle {
    public static void main(String[] args) {
        RentalService service = new RentalService();

        LuxuryCar luxuryCar = new LuxuryCar("Luxury Car A");
        StandardCar standardCar = new StandardCar("Standard Car B");

        service.rentVehicle(luxuryCar, 3);
        service.rentVehicle(standardCar, 5);
        service.returnVehicle(luxuryCar);
    }
}
