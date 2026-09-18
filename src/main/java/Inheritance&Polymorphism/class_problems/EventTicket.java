class EventTicket {
    private String attendeeId;
    private double basePrice;
    private double paidAmount;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.attendeeId = attendeeId.trim();
        this.basePrice = basePrice;
        this.paidAmount = 0.0;
    }

    public void pay(double amount) {
        this.paidAmount += amount;
    }

    public double getBalanceDue() {
        return this.basePrice - this.paidAmount;
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        if (attendeeIds != null) {
            for (String id : attendeeIds) {
                try {
                    new EventTicket(id, basePrice);
                    registered++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}

class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }
}

public class Main7 {
    public static void main(String[] args) {
        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue());

        String[] batch = {"STU1", "ST1", "STU2", "  ", "STU3"};
        System.out.println(EventTicket.registerBatch(batch, 500));
    }
}
