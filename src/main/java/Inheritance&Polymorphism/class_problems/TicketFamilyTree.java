public class TicketFamilyTree {

    public static class EventTicket {
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

        public void printTicket() {
            System.out.println("Standard Event Ticket | Balance Due: " + getBalanceDue());
        }

        public static String classifyGeneration(EventTicket ticket) {
            if (ticket instanceof PremiumWorkshopTicket) {
                return "Multilevel descendant (3 generations deep)";
            } else if (ticket instanceof HackathonTicket) {
                return "Hierarchical sibling (independent branch)";
            } else if (ticket instanceof WorkshopTicket) {
                return "Multilevel branch (2 generations deep)";
            }
            return "Root base class";
        }

        public static double getTotalBalanceDue(EventTicket[] tickets) {
            double total = 0.0;
            if (tickets != null) {
                for (EventTicket ticket : tickets) {
                    if (ticket != null) {
                        total += ticket.getBalanceDue();
                    }
                }
            }
            return total;
        }
    }

    public static class WorkshopTicket extends EventTicket {
        private String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }

        @Override
        public void printTicket() {
            System.out.println("Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue());
        }
    }

    public static class PremiumWorkshopTicket extends WorkshopTicket {
        private double kitFee;

        public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
            super(attendeeId, basePrice, track);
            this.kitFee = kitFee;
        }

        @Override
        public void printTicket() {
            System.out.println("Premium Workshop Ticket | Track: " + getTrack() + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue());
        }
    }

    public static class HackathonTicket extends EventTicket {
        private String teamName;

        public HackathonTicket(String attendeeId, double basePrice, String teamName) {
            super(attendeeId, basePrice);
            this.teamName = teamName;
        }

        @Override
        public void printTicket() {
            System.out.println("Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue());
        }
    }

    public static void main(String[] args) {
        EventTicket standardTicket = new EventTicket("STU1", 500);
        WorkshopTicket workshopTicket = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premiumTicket = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathonTicket = new HackathonTicket("STU4", 800, "Byte Force");

        standardTicket.printTicket();
        workshopTicket.printTicket();
        premiumTicket.printTicket();
        hackathonTicket.printTicket();

        System.out.println(EventTicket.classifyGeneration(premiumTicket));
        System.out.println(EventTicket.classifyGeneration(hackathonTicket));

        EventTicket[] tickets = {standardTicket, workshopTicket, premiumTicket, hackathonTicket};
        System.out.println(EventTicket.getTotalBalanceDue(tickets));
    }
}
