import java.util.HashSet;
import java.util.Set;

public class BusTicket {
    private final String passengerName;
    private final String destination;
    private boolean checkedIn;
    private BusTicket() {
        throw new UnsupportedOperationException("No-argument constructor is not allowed.");
    }

    public BusTicket(String passengerName, String destination) {
        if (!isValid(passengerName) || !isValid(destination)) {
            throw new IllegalArgumentException("Invalid passenger name or destination.");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }

    private static boolean isValid(String field) {
        return field != null && !field.trim().isEmpty() && field.trim().matches("^[a-zA-Z\\s]+$");
    }

    public void markCheckedIn() {
        if (this.checkedIn) {
            throw new IllegalStateException("Ticket has already been checked in.");
        }
        this.checkedIn = true;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getDestination() {
        return destination;
    }

    public static void processBatch(String[][] rawBookings) {
        int validCount = 0;
        int rejectedCount = 0;
        int duplicateCount = 0;

        Set<String> acceptedBookings = new HashSet<>();

        if (rawBookings != null) {
            for (String[] entry : rawBookings) {
                if (entry == null || entry.length < 2) {
                    rejectedCount++;
                    continue;
                }

                try {
                    BusTicket ticket = new BusTicket(entry[0], entry[1]);
                    String bookingKey = ticket.getPassengerName().toLowerCase() + "||" + ticket.getDestination().toLowerCase();

                    if (acceptedBookings.contains(bookingKey)) {
                        duplicateCount++;
                    } else {
                        acceptedBookings.add(bookingKey);
                        validCount++;
                    }
                } catch (IllegalArgumentException e) {
                    rejectedCount++;
                }
            }
        }

        System.out.printf("Valid: %d | Rejected: %d | Duplicates skipped: %d%n", 
                          validCount, rejectedCount, duplicateCount);
    }
}
