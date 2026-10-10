import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

enum ReservationStatus {
    CONFIRMED,
    CANCELLED
}

abstract class Room {
    private String roomNumber;
    private String roomName;

    public Room(String roomNumber, String roomName) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public abstract double calculateTotalPrice(long nights);
}

class StandardRoom extends Room {
    private static final double NIGHTLY_RATE = 150.00;

    public StandardRoom(String roomNumber, String roomName) {
        super(roomNumber, roomName);
    }

    @Override
    public double calculateTotalPrice(long nights) {
        return NIGHTLY_RATE * nights;
    }
}

class DeluxeRoom extends Room {
    private static final double NIGHTLY_RATE = 200.00;

    public DeluxeRoom(String roomNumber, String roomName) {
        super(roomNumber, roomName);
    }

    @Override
    public double calculateTotalPrice(long nights) {
        return NIGHTLY_RATE * nights;
    }
}

class SuiteRoom extends Room {
    private static final double NIGHTLY_RATE = 350.00;

    public SuiteRoom(String roomNumber, String roomName) {
        super(roomNumber, roomName);
    }

    @Override
    public double calculateTotalPrice(long nights) {
        return NIGHTLY_RATE * nights;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Reservation {
    private static int idCounter = 1001;
    private String reservationId;
    private Customer customer;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    private ReservationStatus status;

    public Reservation(Customer customer, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationId = "RES-" + idCounter++;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        this.totalPrice = room.calculateTotalPrice(nights);
        this.status = ReservationStatus.CONFIRMED;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        if (status == ReservationStatus.CANCELLED) {
            return false;
        }
        return checkInDate.isBefore(end) && start.isBefore(checkOutDate);
    }
}

class BookingManager {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public BookingManager() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        for (Reservation res : reservations) {
            if (res.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                if (res.overlaps(checkIn, checkOut)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Reservation bookRoom(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        if (!isRoomAvailable(room, checkIn, checkOut)) {
            System.out.println("Booking failed: " + room.getRoomName() + " is not available for " + checkIn + " to " + checkOut + ".");
            return null;
        }
        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        reservations.add(reservation);
        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n",
                room.getRoomName(), checkIn, checkOut, reservation.getTotalPrice());
        return reservation;
    }

    public boolean cancelReservation(Reservation reservation, LocalDate currentDate) {
        if (reservation == null || reservation.getStatus() == ReservationStatus.CANCELLED) {
            return false;
        }
        if (currentDate.isBefore(reservation.getCheckInDate())) {
            reservation.setStatus(ReservationStatus.CANCELLED);
            System.out.println("Reservation for " + reservation.getRoom().getRoomName() + " cancelled successfully.");
            return true;
        } else {
            System.out.println("Cancellation failed: Reservation cannot be cancelled on or after check-in date.");
            return false;
        }
    }
}

public class HotelBookingAndCancellation {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();

        Room deluxe101 = new DeluxeRoom("101", "Deluxe Room 101");
        Room standard205 = new StandardRoom("205", "Standard Room 205");

        manager.addRoom(deluxe101);
        manager.addRoom(standard205);

        Customer customer = new Customer("C01", "Student");

        Reservation res1 = manager.bookRoom(customer, deluxe101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        Reservation res2 = manager.bookRoom(customer, standard205, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        Reservation res3 = manager.bookRoom(customer, deluxe101, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));

        if (res1 != null) {
            manager.cancelReservation(res1, LocalDate.of(2024, 11, 30));
        }
    }
}
