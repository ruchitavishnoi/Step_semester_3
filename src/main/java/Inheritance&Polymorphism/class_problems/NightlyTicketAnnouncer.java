public class NightlyTicketAnnouncer {

    public static class EventTicket {
        private double basePrice;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
        }

        public double getBalanceDue() {
            return basePrice;
        }

        public String printTicket() {
            return "Standard | Balance: " + String.format("%.1f", getBalanceDue());
        }
    }

    public static class WorkshopTicket extends EventTicket {
        private String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }

        @Override
        public String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + String.format("%.1f", getBalanceDue());
        }
    }

    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();
        if (tickets != null) {
            for (EventTicket ticket : tickets) {
                sb.append(ticket.printTicket()).append(" ");
                if (ticket instanceof WorkshopTicket) {
                    WorkshopTicket wt = (WorkshopTicket) ticket;
                    sb.append("[Track via downcast: ").append(wt.getTrack()).append("] ");
                }
                sb.append("| ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };
        System.out.println(batchPrint(tickets));
    }
}
